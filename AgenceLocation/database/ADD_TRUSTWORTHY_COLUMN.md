# Add Trustworthy Column to Clients Table

## SQL Script

Run this SQL command to add the `trustworthy` column:

```sql
ALTER TABLE clients ADD COLUMN trustworthy BOOLEAN DEFAULT 1;
```

## How to Run

### Option 1: Using SQLite Command Line
```bash
sqlite3 database/agencelocation.db < database/add_trustworthy_column.sql
```

Or interactively:
```bash
sqlite3 database/agencelocation.db
sqlite> ALTER TABLE clients ADD COLUMN trustworthy BOOLEAN DEFAULT 1;
sqlite> .quit
```

### Option 2: Using DB Browser for SQLite
1. Open `database/agencelocation.db` in DB Browser
2. Go to **Execute SQL** tab
3. Paste the ALTER TABLE command
4. Click **Execute**

### Option 3: Using Java Code
You can also run it programmatically:

```java
try (Connection conn = DatabaseConnection.getConnection();
     Statement stmt = conn.createStatement()) {
    stmt.execute("ALTER TABLE clients ADD COLUMN trustworthy BOOLEAN DEFAULT 1");
    System.out.println("Column added successfully!");
} catch (SQLException e) {
    if (e.getMessage().contains("duplicate column")) {
        System.out.println("Column already exists");
    } else {
        e.printStackTrace();
    }
}
```

## Column Details

- **Name**: `trustworthy`
- **Type**: `BOOLEAN` (stored as INTEGER: 0 = false, 1 = true)
- **Default**: `1` (true) - all existing clients will be marked as trustworthy
- **Nullable**: Yes (can be NULL)

## Update Existing Data (Optional)

After adding the column, you might want to update existing clients:

```sql
-- Set all existing clients to trustworthy (default already does this)
UPDATE clients SET trustworthy = 1 WHERE trustworthy IS NULL;

-- Or set specific clients to untrustworthy based on criteria
-- Example: Clients with cancelled reservations
UPDATE clients 
SET trustworthy = 0 
WHERE id IN (
    SELECT DISTINCT client_id 
    FROM reservations 
    WHERE statut = 'annule'
);
```

## Verify Column Was Added

```sql
-- Check table structure
PRAGMA table_info(clients);

-- Or
SELECT sql FROM sqlite_master WHERE type='table' AND name='clients';
```

## Notes

- If the column already exists, you'll get an error. That's OK - it means it's already there.
- The default value (1 = true) will be applied to all existing rows automatically.
- New clients added after this will default to trustworthy = true unless specified otherwise.



