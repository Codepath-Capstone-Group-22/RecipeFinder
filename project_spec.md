# **RecipeFinder**

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description 

**RecipeFinder is a simple, user-friendly Android app that lets users find recipes by entering a list of ingredients and selecting dietary restrictions. It utilizes the Spoonacular API to fetch and display recipe results. Users can "save" or "favorite" recipes to view them later.**

### App Evaluation

<!-- Evaluation of your app across the following attributes -->

- **Category:** Lifestyle
- **Mobile:**  The app is built for mobile, with quick input and a smooth, responsive design.
- **Story:** It helps answer the common question, “What can I cook with what I have?”, which is something many people deal with.
- **Market:** It can be useful for lots of people, especially home cooks, students, and anyone with dietary needs.
- **Habit:** People might use it a few times a week when cooking or planning meals.
- **Scope:** The basic version is simple enough to build, but still useful and worth showing.

## Product Spec

### 1. User Features (Required and Optional)

Required Features:
- [x] User can enter a list of ingredients (comma-separated)
- [x] User can select one dietary filter (e.g., vegan, gluten-free)
- [x] App fetches recipes from the API based on the search
- [x] Recipes are displayed in a scrollable RecyclerView

Stretch Features:
- [ ] User can tap "Save" on a recipe to increase a saved count
- [ ] Saved count is displayed in the UI
- [ ] Saved count stays after app is reloaded

### 2. Chosen API(s)

- Endpoint: `https://api.spoonacular.com/recipes/complexSearch`
    - Used to fetch recipes based on ingredients and dietary restrictions
    - Supports filters like includeIngredients, diet, number, apiKey...
    - Associated Required Feature: Fetch and display recipes matching user input

### 3. User Interaction
Required Features:
- [x] User enters ingredients and taps "Search" > App calls Spoonacular API and displays recipe results
- [x] User selects dietary restriction(s) and taps "Search" > App calls Spoonacular API and displays recipe results
- [ ] User taps "Save" on a recipe card > Saved count increases and updates in the UI

Stretch Features:
- [ ] User opens the app again later > Saved count remains?

## Wireframes

<!-- Add picture of your hand sketched wireframes in this section -->
Our initial digital wireframe:
<img src="https://i.imgur.com/OUfJa1O.png" width=600>

Our final hand sketched wireframe:
<img src="https://i.imgur.com/ZctfMo2.png" width=600>

## Build Notes

Here's a place for any other notes on the app, it's creation 
process, or what you learned this unit!  

For Milestone 2, include **2+ Videos/GIFs** of the build process here!

## License

Copyright **2025** **your name**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
