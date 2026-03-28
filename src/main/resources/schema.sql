
-- USERS TABLE
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password VARCHAR(100) NOT NULL,
                                     role VARCHAR(20) NOT NULL,
                                     is_locked BOOLEAN DEFAULT FALSE
);

-- ACCESS LOGS TABLE
CREATE TABLE IF NOT EXISTS access_logs (
                                           id SERIAL PRIMARY KEY,
                                           user_id INTEGER,
                                           login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           ip_address VARCHAR(50),
                                           status VARCHAR(50),
                                           CONSTRAINT access_logs_status_check CHECK (
                                               status IN (
                                                          'SUCCESS',
                                                          'FAILED',
                                                          'FAILED_USER_NOT_FOUND',
                                                          'BLOCKED_ATTEMPT',
                                                          'ACCOUNT_LOCKED_BY_SYSTEM'
                                                   )
                                               ),
                                           CONSTRAINT access_logs_user_id_fkey
                                               FOREIGN KEY (user_id)
                                                   REFERENCES users(id)
                                                   ON DELETE CASCADE
);

-- THREAT ALERTS TABLE
CREATE TABLE IF NOT EXISTS threat_alerts (
                                             id SERIAL PRIMARY KEY,
                                             user_id INTEGER,
                                             reason VARCHAR(255),
                                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                             CONSTRAINT threat_alerts_user_id_fkey
                                                 FOREIGN KEY (user_id)
                                                     REFERENCES users(id)
);


-- FUNCTION (SPRING SAFE)
CREATE OR REPLACE FUNCTION detect_failed_logins()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS '
    DECLARE
        fail_count INT;
    BEGIN
        IF NEW.status = ''FAILED'' THEN

            SELECT COUNT(*) INTO fail_count
            FROM access_logs
            WHERE user_id = NEW.user_id
              AND status = ''FAILED''
              AND login_time >= NOW() - INTERVAL ''5 minutes'';

            IF fail_count >= 3 THEN

                INSERT INTO threat_alerts(user_id, reason)
                VALUES (NEW.user_id, ''Multiple failed logins within 5 minutes'');

                UPDATE users
                SET is_locked = TRUE
                WHERE id = NEW.user_id;

            END IF;

        END IF;

        RETURN NEW;
    END;
';

-- TRIGGER
DROP TRIGGER IF EXISTS trigger_detect_failed_logins ON access_logs;

CREATE TRIGGER trigger_detect_failed_logins
    AFTER INSERT ON access_logs
    FOR EACH ROW
EXECUTE FUNCTION detect_failed_logins();