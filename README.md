PackList -- Packing Checklist
PackList is a simple Android application that helps travellers prepare
for a trip by creating and managing a packing checklist. Users can add
items, organise them by category, mark them as packed, and track their
packing progress.
Project Overview
- Application name: PackList
- Platform: Android
- Language: Kotlin
- User interface: XML layouts and Android widgets
- Database: SQLite
- Package name: com.example.madia2
Features
1. Packing Checklist
- Displays the user's packing items in a list.
- Each item has a checkbox to mark it as packed or unpacked.
- Stores each item's name, category, and packed status.
2. Add and Edit Items
- Allows users to add new packing items.
- Supports the following categories:
  - Clothes
  - Documents
  - Gadgets
- Supports editing an existing item.
3. Packing Progress
- Displays the number of packed items out of the total number of
  items.
- Uses a progress bar to show the completion percentage.
- Updates when an item's packed status changes.
4. Menu Options
The options menu provides: - Uncheck All: Marks all items as
unpacked. - Delete Packed Items: Removes all items already marked as
packed. - Add Default Items: Adds a predefined set of packing items.
5. Context Menu
- Long-press an item to access actions for editing or deleting that
  item.
6. Data Persistence
- Uses SQLite to store packing items and their packed status.
- Keeps saved items available after the application is closed and
  reopened.
Technologies Used
  Technology                          Purpose
  Kotlin                              Application logic

  Android SDK                         Android application platform

  XML                                 Screen and row layouts

  SQLite                              Local storage

  Android Widgets                     CheckBox, ListView, Button,
Spinner, ProgressBar and menus
AndroidX AppCompat                  Activity and compatibility support
Project Structure
The main application files are organised as follows:
app/
└── src/
    └── main/
        ├── java/com/example/madia2/
        │   ├── MainActivity.kt
        │   ├── AddItemActivity.kt
        │   ├── DBHelper.kt
        │   └── PackAdapter.kt
        ├── res/
        │   ├── layout/
        │   │   ├── activity_main.xml
        │   │   ├── activity_add_item.xml
        │   │   └── item_pack.xml
        │   ├── menu/
        │   │   └── main_menu.xml
        │   └── values/
        │       └── themes.xml
        └── AndroidManifest.xml
How to Run the Application
1. Open the project in Android Studio.
2. Allow Gradle to sync and download the required dependencies.
3. Connect an Android device with USB debugging enabled, or start an
   Android Emulator.
4. Select the app run configuration.
5. Click Run to install and launch PackList.
How to Use
1. Launch PackList to view the packing checklist.
2. Select Add Item to enter an item name and choose a category.
3. Save the item to add it to the checklist.
4. Tick an item's checkbox when it has been packed.
5. Check the progress text and progress bar to see packing completion.
6. Long-press an item to edit or delete it.
7. Use the options menu to uncheck all items, delete packed items, or
   add default items.
Database
PackList uses a local SQLite database named PackList.db.

The items table stores:

  Column       Description
  id         Unique item identifier
name       Packing item name
category   Item category
packed     Packed status (0 = unpacked, 1 = packed)
Assignment Requirements
This project addresses the PackList -- Packing Checklist task using
SQLite, basic Android widgets, and menus. It includes a checklist with
checkboxes, an item-entry screen with categories, a packing progress
display, options-menu actions, and a context menu for editing or
deleting an individual item.
Author
Sahil Dcunha, MCA, GENERAL, JAIN UNIVERSITY
