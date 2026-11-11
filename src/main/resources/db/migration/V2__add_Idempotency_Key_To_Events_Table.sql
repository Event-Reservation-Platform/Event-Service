ALTER TABLE events
ADD COLUMN idempotency_key UUID;

CREATE UNIQUE INDEX idx_event_idempotency_key
ON events (idempotency_key);