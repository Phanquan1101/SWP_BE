-- Allow report coordinates to be optional in MVP.
-- Legacy columns are kept for compatibility, but citizen no longer has to input them.
ALTER TABLE waste_reports
    MODIFY COLUMN latitude DECIMAL(10,7) NULL,
    MODIFY COLUMN longitude DECIMAL(10,7) NULL;

