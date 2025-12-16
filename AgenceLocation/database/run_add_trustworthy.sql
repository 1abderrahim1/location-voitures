-- ============================================
-- Quick script to add trustworthy column
-- Run this in SQLite to add the column
-- ============================================

-- Add trustworthy column to clients table
ALTER TABLE clients ADD COLUMN trustworthy BOOLEAN DEFAULT 1;

-- Check if column was added successfully
SELECT sql FROM sqlite_master WHERE type='table' AND name='clients';

