🐄 Gokula-Health — Digital Health Card for Cattle

An Android app that helps small-scale dairy farmers digitally manage their cattle's health, milk yield, vaccination schedules, heat cycles, and breeding records — works completely offline.

📌 Problem Statement
Small-scale dairy farmers in India lose significant income because they miss critical cattle health events:
❌ Missed heat cycles → lost breeding opportunities

❌ Forgotten vaccination dates → disease outbreaks like FMD, BQ, HS

❌ No milk yield records → cannot detect declining productivity

❌ No digital health proof → cattle value not recognized for insurance or resale

Livestock health tracking is mostly done orally or on paper with no affordable offline digital solution for rural farmers.

Solution

Gokula-Health is a digital health card for cattle. It helps the farmer manage the complete Life Cycle of their cows. The app tracks milk yield trends, vaccination schedules, heat cycles, and breeding records. It serves as a Health Passport that increases the value of the cattle when being sold or insured.

The app works 100% offline — no internet needed at any point.

Features

🐮 1. Cattle Profile

Register each cow with Ear Tag ID, name, breed, date of birth
Capture and store cow photo from device gallery using Glide
View complete cattle list with profile cards using RecyclerView


🥛 2. Milk Diary

Daily entry of morning and evening milk yield in liters
Auto-calculates total daily yield
Auto-calculates monthly average yield using SQL AVG() query
View complete milk history per cow


💉 3. Vaccination Alert System

Set reminders for FMD, BQ, HS and any other vaccine
AlarmManager-based notifications — fires at 8:00 AM on due date
Works WITHOUT internet — even after phone restarts
View list of all pending vaccinations


📊 4. Milk Yield Graph

30-day line chart using MPAndroidChart library
Visual representation of milk production trends
Monthly average displayed below the chart
Identifies if a cow's production is dropping


🌡️ 5. Heat Cycle Tracker

Record last heat date → auto-calculates next heat date (+21 days)
Record breeding date → marks as Breeding Done
Add notes like bull name or AI details
View complete heat cycle history per cow


📋 6. Health Passport PDF

Generate a PDF document with complete cattle health info
Saved to device Documents folder
Can be shared for insurance or cattle resale




