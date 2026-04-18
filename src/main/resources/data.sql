-- Seed data for CompanyManagement application
-- This file clears existing rows and inserts sample data (25 employees)

DELETE FROM employee;
DELETE FROM project;
DELETE FROM department;
DELETE FROM company;

-- Company
INSERT INTO company (id, name, location) VALUES (1, 'Acme Corp', 'New York');

-- Departments
INSERT INTO department (id, name, company_id) VALUES
  (1, 'Engineering', 1),
  (2, 'HR', 1),
  (3, 'Sales', 1),
  (4, 'Finance', 1);

-- Projects
INSERT INTO project (id, name, budget, department_id) VALUES
  (1, 'Payroll System', 50000.00, 2),
  (2, 'Website Revamp', 150000.00, 1),
  (3, 'Mobile App', 120000.00, 1),
  (4, 'Cloud Migration', 200000.00, 1),
  (5, 'Recruitment Drive', 30000.00, 2),
  (6, 'Quarterly Campaign', 80000.00, 3),
  (7, 'Pricing Optimization', 60000.00, 4),
  (8, 'Analytics Platform', 180000.00, 1);

-- Employees (25 records)
INSERT INTO employee (id, name, salary, project_id) VALUES
  (1, 'Alice', 75000.00, 2),
  (2, 'Bob', 60000.00, 2),
  (3, 'Charlie', 50000.00, 1),
  (4, 'David', 85000.00, 3),
  (5, 'Eve', 72000.00, 3),
  (6, 'Frank', 68000.00, 4),
  (7, 'Grace', 70000.00, 4),
  (8, 'Heidi', 65000.00, 5),
  (9, 'Ivan', 62000.00, 6),
  (10, 'Judy', 64000.00, 6),
  (11, 'Karl', 59000.00, 7),
  (12, 'Laura', 77000.00, 8),
  (13, 'Mallory', 81000.00, 8),
  (14, 'Neil', 55000.00, 2),
  (15, 'Olivia', 73000.00, 3),
  (16, 'Peggy', 58000.00, 1),
  (17, 'Quentin', 66000.00, 4),
  (18, 'Ruth', 71000.00, 5),
  (19, 'Sam', 54000.00, 7),
  (20, 'Trudy', 76000.00, 8),
  (21, 'Uma', 69000.00, 3),
  (22, 'Victor', 63000.00, 6),
  (23, 'Wendy', 78000.00, 2),
  (24, 'Xavier', 56000.00, 7),
  (25, 'Yvonne', 82000.00, 8);
