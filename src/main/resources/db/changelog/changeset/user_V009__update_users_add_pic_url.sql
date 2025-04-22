-- Add new column if it doesn't exist
ALTER TABLE users
ADD COLUMN IF NOT EXISTS pic_url TEXT;

-- Drop old columns if they exist
ALTER TABLE users
DROP COLUMN IF EXISTS profile_pic_file_id,
DROP COLUMN IF EXISTS profile_pic_small_file_id;