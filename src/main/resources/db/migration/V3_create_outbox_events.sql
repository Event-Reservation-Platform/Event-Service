CREATE TABLE outbox_events (
    id UUID primary key,
    event_type varchar(50) not null,
    payload JSONB not null,
    status varchar(30) not null default 'PENDING',
    created_at TIMESTAMP not null default now()
)

CREATE INDEX idx_outbox_pending
ON outbox_events (created_at, id)
WHERE status = 'PENDING'
