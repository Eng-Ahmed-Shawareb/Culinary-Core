
-- ============================================================
-- TEST DATA INSERT SCRIPT  (Full Version — supports all 6 queries)
-- "Last month" = April 2026  (current month assumed = May 2026)
--
-- Designed answers:
--  Q1: 'Italian' expertise has the most enrollments (Homemade Pasta + Tomato Sauce)
--  Q2: 'Japanese Kitchen' (ID=4) had zero workshops in April 2026
--  Q3: 'Fresh Farms Co.' (ID=1) supplied the highest consumed quantity in April 2026
--  Q4: Carlos Mendez (Grilling) and Yuki Tanaka (Japanese) led no workshops in April 2026
--  Q5: Each kitchen that hosted a workshop in April 2026 has ingredient batches consumed
--  Q6: Every student appears with their contact info and a distinct workshop count
--
-- Insertion order (foreign key dependency):
--   1. Supplier → 2. Kitchen → 3. Chef → 4. Ingredient_batch
--   5. Workshop  → 6. Student → 7. Register → 8. Consume
-- ============================================================
 
 
-- ============================================================
-- 1. SUPPLIER
-- ============================================================
INSERT INTO Supplier (name) VALUES
    ('Fresh Farms Co.'),          -- ID 1  ← wins Q3 (highest qty last month)
    ('Mediterranean Imports'),    -- ID 2
    ('Local Harvest Supply'),     -- ID 3
    ('Elite Spice Traders');      -- ID 4
 
 
-- ============================================================
-- 2. KITCHEN
-- ============================================================
INSERT INTO kitchen (name, type) VALUES
    ('Main Culinary Hall',    'Professional'),  -- ID 1  ← has workshops last month
    ('Pastry Studio',         'Baking'),        -- ID 2  ← has workshops last month
    ('Outdoor Grill Kitchen', 'BBQ'),           -- ID 3  ← has workshops last month
    ('Japanese Kitchen',      'Asian Cuisine'); -- ID 4  ← NO workshops last month (Q2)
 
 
-- ============================================================
-- 3. CHEF
-- ============================================================
INSERT INTO chef ([first name], [last name], bio, expertise) VALUES
    ('Ahmed',   'Hassan',   'Award-winning chef with 15 years in Mediterranean cuisine.',   'Mediterranean'), -- ID 1  ← active last month
    ('Layla',   'Ibrahim',  'Pastry specialist trained in Paris, known for delicate work.', 'Pastry'),        -- ID 2  ← active last month
    ('Carlos',  'Mendez',   'BBQ and grill master with a focus on Latin American flavors.', 'Grilling'),      -- ID 3  ← NO workshops last month (Q4)
    ('Yuki',    'Tanaka',   'Expert in Japanese cuisine and traditional knife techniques.', 'Japanese'),      -- ID 4  ← NO workshops last month (Q4)
    ('Sara',    'El-Sayed', 'Italian cuisine expert specializing in fresh pasta and sauces.','Italian');      -- ID 5  ← active last month, wins Q1
 
 
-- ============================================================
-- 4. INGREDIENT_BATCH
-- Batches delivered before April 2026 workshops, expiring after them.
-- Fresh Farms (ID=1) batches will be consumed in large quantities → wins Q3
-- ============================================================
INSERT INTO Ingredient_batch (FK_SupplierID, name, units, [expiration date], [delivery date], state) VALUES
    -- Fresh Farms Co. (ID=1) — large batches, high consumption last month
    (1, 'Tomatoes',        200, '2026-06-01', '2026-03-10', 'Fresh'),        -- ID 1
    (1, 'Zucchini',        150, '2026-06-01', '2026-03-12', 'Fresh'),        -- ID 2
    (1, 'Spinach',         120, '2026-05-15', '2026-03-15', 'Fresh'),        -- ID 3
    -- Mediterranean Imports (ID=2)
    (2, 'Olive Oil',        40, '2027-01-01', '2026-03-01', 'Sealed'),       -- ID 4
    (2, 'Feta Cheese',      50, '2026-07-01', '2026-03-20', 'Refrigerated'), -- ID 5
    -- Local Harvest Supply (ID=3)
    (3, 'Flour',           300, '2026-12-01', '2026-02-01', 'Dry'),          -- ID 6
    (3, 'Butter',           80, '2026-06-10', '2026-03-22', 'Refrigerated'), -- ID 7
    -- Elite Spice Traders (ID=4) — small quantities by nature
    (4, 'Saffron',          12, '2027-06-01', '2026-03-05', 'Dry'),          -- ID 8
    (4, 'Paprika',          30, '2027-03-01', '2026-03-08', 'Dry');          -- ID 9
 
 
-- ============================================================
-- 5. WORKSHOP
-- Last month = April 2026
-- Kitchens active in April: 1 (Main Hall), 2 (Pastry Studio), 3 (Outdoor Grill)
-- Kitchen 4 (Japanese) has NO April 2026 workshops → answers Q2
-- Chefs active in April: 1 (Ahmed), 2 (Layla), 5 (Sara)
-- Chefs NOT in April: 3 (Carlos), 4 (Yuki)          → answers Q4
-- Italian expertise (Sara, ID=5) leads the most-enrolled workshops → answers Q1
-- ============================================================
INSERT INTO Workshop (FK_KitchenID, FK_ChefID, title, price, [start date], [end date], state, technique) VALUES
    -- April 2026 workshops (last month)
    (1, 1, 'Mediterranean Flavors',   350.00, '2026-04-02', '2026-04-04', 'Completed', 'Braising'),       -- ID 1  Kitchen=Main Hall,    Chef=Ahmed
    (2, 2, 'French Pastry Basics',    280.00, '2026-04-07', '2026-04-08', 'Completed', 'Lamination'),     -- ID 2  Kitchen=Pastry Studio, Chef=Layla
    (1, 5, 'Homemade Pasta',          250.00, '2026-04-14', '2026-04-14', 'Completed', 'Rolling'),        -- ID 3  Kitchen=Main Hall,    Chef=Sara  (Italian)
    (3, 5, 'Tomato Sauce Secrets',    200.00, '2026-04-21', '2026-04-21', 'Completed', 'Sauteing'),       -- ID 4  Kitchen=Outdoor Grill, Chef=Sara  (Italian) ← Sara leads 2 workshops
    (2, 2, 'Croissant Workshop',      300.00, '2026-04-28', '2026-04-29', 'Completed', 'Proofing'),       -- ID 5  Kitchen=Pastry Studio, Chef=Layla
 
    -- Older workshops (before last month — for Q6 total count history)
    (4, 4, 'Sushi & Knife Skills',    500.00, '2026-02-10', '2026-02-12', 'Completed', 'Filleting'),      -- ID 6  Kitchen=Japanese (old, so NOT counted in Q2)
    (3, 3, 'BBQ Masterclass',         400.00, '2026-02-20', '2026-02-22', 'Completed', 'Smoking'),        -- ID 7  Kitchen=Outdoor Grill (old)
    (1, 1, 'Moroccan Tagine',         300.00, '2026-03-05', '2026-03-06', 'Completed', 'Slow Cooking'),   -- ID 8  Kitchen=Main Hall (old)
 
    -- Future workshops (upcoming — excluded from last-month queries)
    (4, 4, 'Advanced Sushi',          550.00, '2026-06-10', '2026-06-12', 'Scheduled', 'Filleting'),      -- ID 9
    (3, 3, 'Grilling Techniques',     380.00, '2026-06-18', '2026-06-19', 'Scheduled', 'Smoking');        -- ID 10
 
 
-- ============================================================
-- 6. STUDENT
-- ============================================================
INSERT INTO student ([first name], [last name], gender, phone) VALUES
    ('Omar',    'Khalil',   'M', '010-1234-5678'),  -- ID 1
    ('Nadia',   'Farouk',  'F', '010-2345-6789'),  -- ID 2
    ('Tarek',   'Mansour', 'M', '012-3456-7890'),  -- ID 3
    ('Hana',    'Samir',   'F', '011-4567-8901'),  -- ID 4
    ('Karim',   'Abdalla', 'M', '010-5678-9012'),  -- ID 5
    ('Mona',    'Zaki',    'F', '012-6789-0123'),  -- ID 6
    ('Youssef', 'Gaber',   'M', NULL),             -- ID 7  ← no phone (tests Q6 NULLs)
    ('Dina',    'Fouad',   'F', '011-7890-1234');  -- ID 8
 
 
-- ============================================================
-- 7. REGISTER
-- Enrollment breakdown by workshop (for Q1 — expertise enrollment count):
--   Italian workshops (ID 3,4 = Homemade Pasta + Tomato Sauce):
--     Workshop 3: students 1,2,3,4,5     → 5 enrollments
--     Workshop 4: students 1,2,6,7,8     → 5 enrollments
--     Total Italian = 10  ← WINS Q1
--   Pastry workshops (ID 2,5):
--     Workshop 2: students 3,4           → 2
--     Workshop 5: students 4,6           → 2
--     Total Pastry = 4
--   Mediterranean workshops (ID 1,8):
--     Workshop 1: students 5,6           → 2
--     Workshop 8: students 7             → 1
--     Total Mediterranean = 3
--   Japanese (ID 6): students 6,7,8     → 3
--   Grilling  (ID 7): students 1,8      → 2
--
-- Q6 total workshops per student:
--   Omar(1)=3, Nadia(2)=3, Tarek(3)=2, Hana(4)=3,
--   Karim(5)=2, Mona(6)=3, Youssef(7)=2, Dina(8)=3
-- ============================================================
INSERT INTO Register (FK_StudentID, FK_WorkshopID, [register date], state) VALUES
    -- Workshop 1: Mediterranean Flavors (April 2026)
    (5, 1, '2026-03-15', 'Confirmed'),
    (6, 1, '2026-03-16', 'Confirmed'),
 
    -- Workshop 2: French Pastry Basics (April 2026)
    (3, 2, '2026-03-18', 'Confirmed'),
    (4, 2, '2026-03-19', 'Confirmed'),
 
    -- Workshop 3: Homemade Pasta (April 2026) ← Italian, 5 students
    (1, 3, '2026-03-20', 'Confirmed'),
    (2, 3, '2026-03-20', 'Confirmed'),
    (3, 3, '2026-03-21', 'Confirmed'),
    (4, 3, '2026-03-21', 'Confirmed'),
    (5, 3, '2026-03-22', 'Confirmed'),
 
    -- Workshop 4: Tomato Sauce Secrets (April 2026) ← Italian, 5 students
    (1, 4, '2026-03-25', 'Confirmed'),
    (2, 4, '2026-03-25', 'Confirmed'),
    (6, 4, '2026-03-26', 'Confirmed'),
    (7, 4, '2026-03-26', 'Confirmed'),
    (8, 4, '2026-03-27', 'Confirmed'),
 
    -- Workshop 5: Croissant Workshop (April 2026)
    (4, 5, '2026-04-01', 'Confirmed'),
    (6, 5, '2026-04-01', 'Confirmed'),
 
    -- Workshop 6: Sushi & Knife Skills (Feb 2026, old)
    (6, 6, '2026-01-20', 'Confirmed'),
    (7, 6, '2026-01-21', 'Confirmed'),
    (8, 6, '2026-01-22', 'Confirmed'),
 
    -- Workshop 7: BBQ Masterclass (Feb 2026, old)
    (1, 7, '2026-01-25', 'Confirmed'),
    (8, 7, '2026-01-26', 'Confirmed'),
 
    -- Workshop 8: Moroccan Tagine (Mar 2026, old)
    (2, 8, '2026-02-10', 'Confirmed'),
    (4, 8, '2026-02-11', 'Confirmed'),
    (7, 8, '2026-02-12', 'Confirmed');
 
 
-- ============================================================
-- 8. CONSUME
-- "Last month" = April 2026
-- Fresh Farms batches (ID 1,2,3 = Tomatoes, Zucchini, Spinach):
--   total qty = 60+50+45 = 155  ← WINS Q3
-- Mediterranean Imports batches (ID 4,5):
--   total qty = 10+8 = 18
-- Local Harvest batches (ID 6,7):
--   total qty = 40+20 = 60
-- Elite Spice (ID 8,9):
--   total qty = 2+8 = 10
--
-- Q5: Ingredient batches per kitchen last month
--   Main Hall    (ID=1): batches 1,2,4     (workshops 1,3)
--   Pastry Studio(ID=2): batches 6,7,3     (workshops 2,5)
--   Outdoor Grill(ID=3): batches 1,3,9     (workshop 4)
--   Japanese     (ID=4): none last month   (ties into Q2)
-- ============================================================
INSERT INTO Consume (FK_BatchID, FK_WorkshopID, qantity, [consuming date]) VALUES
    -- Workshop 1: Mediterranean Flavors  →  Kitchen=Main Hall
    (1, 1, 30, '2026-04-02'),   -- Tomatoes      (Fresh Farms)
    (2, 1, 20, '2026-04-02'),   -- Zucchini      (Fresh Farms)
    (4, 1, 10, '2026-04-03'),   -- Olive Oil     (Mediterranean Imports)
 
    -- Workshop 2: French Pastry Basics   →  Kitchen=Pastry Studio
    (6, 2, 40, '2026-04-07'),   -- Flour         (Local Harvest)
    (7, 2, 20, '2026-04-07'),   -- Butter        (Local Harvest)
    (4, 2,  8, '2026-04-08'),   -- Olive Oil     (Mediterranean Imports)  ← NOTE: (4,2) unique PK
 
    -- Workshop 3: Homemade Pasta         →  Kitchen=Main Hall
    (1, 3, 30, '2026-04-14'),   -- Tomatoes      (Fresh Farms)
    (2, 3, 30, '2026-04-14'),   -- Zucchini      (Fresh Farms)
 
    -- Workshop 4: Tomato Sauce Secrets   →  Kitchen=Outdoor Grill
    (1, 4, 20, '2026-04-21'),   -- Tomatoes      (Fresh Farms)   NOTE: (1,4) unique PK ✓
    (3, 4, 45, '2026-04-21'),   -- Spinach       (Fresh Farms)
    (9, 4,  8, '2026-04-21'),   -- Paprika       (Elite Spice)
 
    -- Workshop 5: Croissant Workshop     →  Kitchen=Pastry Studio
    (5, 5,  8, '2026-04-28'),   -- Feta Cheese   (Mediterranean Imports)
    (6, 5, 15, '2026-04-28'),   -- Flour         (Local Harvest)  NOTE: (6,5) unique PK ✓
    (8, 5,  2, '2026-04-29'),   -- Saffron       (Elite Spice)
 
    -- Older consume records (pre-April — for historical completeness)
    (5, 6, 12, '2026-02-10'),   -- Feta in Sushi workshop (Feb)
    (9, 7, 15, '2026-02-20'),   -- Paprika in BBQ (Feb)
    (1, 8, 25, '2026-03-05'),   -- Tomatoes in Tagine (Mar)
    (8, 8,  3, '2026-03-05');   -- Saffron in Tagine (Mar)
 
 
-- ============================================================
-- EXPECTED QUERY RESULTS SUMMARY
-- ============================================================
-- Q1  MAX enrollments by expertise:
--       Italian = 10 (workshops 3+4), Pastry = 4, Mediterranean = 3 → Italian WINS
--
-- Q2  Kitchens with NO workshops in April 2026:
--       Japanese Kitchen (ID=4) → no rows in Workshop where start date in April 2026
--
-- Q3  Supplier with highest total consumed qty in April 2026:
--       Fresh Farms Co.: Tomatoes(30+30+20) + Zucchini(20+30) + Spinach(45) = 175
--       Local Harvest:   Flour(40+15) + Butter(20) = 75
--       Mediterranean:   Olive Oil(10+8) + Feta(8) = 26
--       Elite Spice:     Paprika(8) + Saffron(2) = 10   → Fresh Farms WINS
--
-- Q4  Chefs with NO workshops in April 2026:
--       Carlos Mendez (Grilling, ID=3) — no April workshops
--       Yuki Tanaka   (Japanese, ID=4) — no April workshops
--
-- Q5  Ingredient batches used per kitchen in April 2026:
--       Main Hall     → Tomatoes, Zucchini, Olive Oil, Tomatoes(w3), Zucchini(w3)
--       Pastry Studio → Flour, Butter, Olive Oil(w2), Feta, Flour(w5), Saffron
--       Outdoor Grill → Tomatoes, Spinach, Paprika
--       Japanese      → (none)
--
-- Q6  Students with contact info + total workshops attended (all time):
--       Omar    | 010-1234-5678 | 3 (workshops 3,4,7)
--       Nadia   | 010-2345-6789 | 3 (workshops 3,4,8)
--       Tarek   | 012-3456-7890 | 2 (workshops 2,3)
--       Hana    | 011-4567-8901 | 3 (workshops 2,3,8)  [also 5 → 4 total if Pending counted]
--       Karim   | 010-5678-9012 | 2 (workshops 1,3)
--       Mona    | 012-6789-0123 | 3 (workshops 1,4,5,6)  → 4 total
--       Youssef | NULL          | 2 (workshops 4,6,8)    → 3 total
--       Dina    | 011-7890-1234 | 2 (workshops 4,6,7)    → 3 total
-- ============================================================




----
DELETE FROM Consume;
DELETE FROM Register;
DELETE FROM Ingredient_batch;
DELETE FROM Workshop;
DELETE FROM student;
DELETE FROM chef;
DELETE FROM kitchen;
DELETE FROM Supplier;
 

select * from Workshop

select * from chef

select * from kitchen

select * from Ingredient_batch

select * from student

select * from Register
