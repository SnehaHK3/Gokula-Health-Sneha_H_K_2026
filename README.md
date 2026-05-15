🐄 Gokula-Health — Digital Health Card for Cattle

An Android app that helps small-scale dairy farmers digitally manage their cattle's health, milk yield, vaccination schedules, heat cycles, and breeding records — works completely offline.

Problem Statement
Small-scale dairy farmers in India lose significant income because they miss critical cattle health events:

❌ Missed heat cycles → lost breeding opportunities
❌ Forgotten vaccination dates → disease outbreaks like FMD, BQ, HS
❌ No milk yield records → cannot detect declining productivity
❌ No digital health proof → cattle value not recognized for insurance or resale

Livestock health tracking is mostly done orally or on paper with no affordable offline digital solution for rural farmers.

💡 Solution
Gokula-Health is a digital health card for cattle. It helps the farmer manage the complete Life Cycle of their cows. The app tracks milk yield trends, vaccination schedules, heat cycles, and breeding records. It serves as a Health Passport that increases the value of the cattle when being sold or insured.
The app works 100% offline — no internet needed at any point.

✨ Features
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


🛠️ Tech Stack
TechnologyVersionPurposeKotlin2.1.0Primary programming languageAndroid StudioPanda 2025.3.1Development environmentRoom Database2.8.4Offline SQLite storageMPAndroidChartv3.1.030-day milk yield line chartAlarmManagerAndroid APIOffline vaccination remindersGlide4.16.0Load and display cow photosLiveData + ViewModel2.8.7Real-time UI updatesKotlin Coroutines1.8.1Background database operationsAndroid PdfDocumentBuilt-in APIHealth passport PDF exportMaterial Design 31.13.0Modern intuitive UI componentsKSP2.1.0-1.0.29Code generation for Room DBGradle8.11.1Build system

📁 Project Structure
GokulaHealth/
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/gokulahealth/
│           ├── MainActivity.kt                   ← Main dashboard (6 buttons)
│           ├── data/
│           │   ├── Cattle.kt                     ← Cattle entity
│           │   ├── MilkEntry.kt                  ← Milk entry entity
│           │   ├── Vaccination.kt                ← Vaccination entity
│           │   ├── HeatCycle.kt                  ← Heat cycle entity
│           │   ├── CattleDao.kt                  ← Cattle DAO
│           │   ├── MilkDao.kt                    ← Milk DAO with AVG query
│           │   ├── VaccinationDao.kt             ← Vaccination DAO
│           │   ├── HeatCycleDao.kt               ← Heat cycle DAO
│           │   └── AppDatabase.kt                ← Room database instance
│           ├── ui/
│           │   ├── CattleListActivity.kt         ← View all cattle
│           │   ├── AddCattleActivity.kt          ← Register new cattle
│           │   ├── MilkDiaryActivity.kt          ← Daily milk entry
│           │   ├── VaccinationActivity.kt        ← Vaccination reminders
│           │   ├── YieldGraphActivity.kt         ← 30-day chart
│           │   ├── HeatCycleActivity.kt          ← Heat cycle tracker
│           │   └── HealthPassportActivity.kt     ← PDF export
│           ├── adapter/
│           │   ├── CattleAdapter.kt              ← RecyclerView adapter
│           │   └── MilkAdapter.kt                ← RecyclerView adapter
│           └── receiver/
│               └── VaccinationReceiver.kt        ← BroadcastReceiver for alarms
├── build.gradle.kts                              ← App dependencies
├── settings.gradle.kts                           ← Project settings
└── gradle-wrapper.properties                     ← Gradle version config

🚀 Setup and Run Instructions
Prerequisites

Android Studio Panda 2025.3.1 or later
Android SDK API 23 (Marshmallow) or higher
JDK 21
Minimum 8GB RAM recommended for emulator

Step 1 — Clone the Repository
bashgit clone https://github.com/SnehaHK3/Gokula-Health-Sneha_H_K_2026.git
cd Gokula-Health-Sneha_H_K_2026
Step 2 — Open in Android Studio

Open Android Studio
Click File → Open
Select the cloned project folder
Wait for Gradle sync to complete (2–5 minutes)
All dependencies download automatically — no manual setup needed

Step 3 — Run the App
Option A — Physical Android Device:

Enable Developer Options on your Android phone
Enable USB Debugging
Connect via USB cable
Click ▶️ Run in Android Studio

Option B — Android Emulator:

Open Device Manager in Android Studio
Create virtual device: Pixel 6, API 34
Click ▶️ to start the emulator
Click ▶️ Run in Android Studio

Step 4 — Build Debug APK
Build → Generate Signed Bundle/APK → APK → Debug → Finish

📊 Database Schema
cattle_table
ColumnTypeDescriptionidInt (PK)Auto-generated primary keyearTagIdStringUnique tag e.g. COW-001nameStringCow's namebreedStringHF / Jersey / GirdateOfBirthStringDD/MM/YYYYphotoPathStringGallery URI pathregisteredDateStringRegistration date
milk_table
ColumnTypeDescriptionidInt (PK)Auto-generated primary keycattleIdInt (FK)References cattle_tabledateStringEntry date DD/MM/YYYYmorningLitersFloatMorning yield in literseveningLitersFloatEvening yield in literstotalLitersFloatAuto-calculated sum
vaccination_table
ColumnTypeDescriptionidInt (PK)Auto-generated primary keycattleIdInt (FK)References cattle_tablevaccineNameStringFMD / BQ / HS / otherdueDateStringDue date DD/MM/YYYYisCompletedBooleanCompletion status
heat_cycle_table
ColumnTypeDescriptionidInt (PK)Auto-generated primary keycattleIdInt (FK)References cattle_tablelastHeatDateStringLast observed heat datenextHeatDateStringAuto-calculated +21 daysbreedingDateStringActual breeding dateisBreedingDoneBooleanBreeding completion statusnotesStringBull name / AI details

✅ Success Criteria
CriteriaImplementationStatusMonthly Average Yield auto-calculatedSQL AVG() query in MilkDao with month pattern✅ DoneReminders work without internetAlarmManager + BOOT_COMPLETED receiver✅ DoneUI uses cow/milk/syringe iconsEmoji icons + Material Design buttons✅ DoneCattle profile with Ear Tag and photoGallery picker + Glide image loading✅ Done30-day yield visualizationMPAndroidChart LineChart with gradient fill✅ DoneHeat cycle auto-calculationLast heat date + 21 days via Calendar API✅ DoneHealth Passport exportAndroid PdfDocument API → Downloads folder✅ Done

🔧 Key Technical Implementations
Offline Vaccination Alarm
kotlin// Works without internet — fires at 8AM on due date
alarmManager.setExactAndAllowWhileIdle(
    AlarmManager.RTC_WAKEUP,
    triggerTime,
    pendingIntent
)
Monthly Average Yield Query
kotlin@Query("SELECT AVG(totalLiters) FROM milk_table
        WHERE cattleId = :cattleId AND date LIKE :monthPattern")
fun getMonthlyAverage(cattleId: Int, monthPattern: String): LiveData<Float>
Heat Cycle Auto-Calculation
kotlin// Cow heat cycle = 21 days
calendar.add(Calendar.DAY_OF_MONTH, 21)
val nextHeatDate = sdf.format(calendar.time)

📋 Android Permissions
xml<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM"/>
<uses-permission android:name="android.permission.USE_EXACT_ALARM"/>
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="28"/>

🌟 Impact Goals
GoalHow App Achieves It🥛 White Revolution 2.0Milk Diary + 30-day yield graph for data-driven farm management💰 Farmer IncomeAutomated offline vaccination alerts → 100% vaccination coverage📱 Rural DigitizationOffline-first app with simple icon-based UI for rural farmers

👩‍💻 Developer
Sneha H K

🐙 GitHub: @SnehaHK3
🎓 Track: Android App Development Using GenAI
🌾 Domain: Agriculture / Livestock Management




