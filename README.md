PUBLIC ONSITE OUTHOUSE PATHFINDER (P.O.O.P.) - README
===
 
# PUBLIC ONSITE OUTHOUSE PATHFINDER (P.O.O.P.)

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
An app that displays the locations of nearby toilets/restrooms through the use of GPS.

### App Evaluation
- **Category:** Health
- **Mobile:** App is primarily designed for mobile devices
- **Story:** Collect submitted information from users and display them in a way easy to parse through.
- **Market:** Individuals who need to locate a restroom (in its current state, must be on CPP campus)
- **Habit:** The app would be used any time you need to find/rate a restroom.
- **Scope:** We would start by using our own database to store the restroom locations/ratings. The app could potentially be implemented into Google maps.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [x] User can find themselves and any restrooms on a map
   - <img src='POOP 11-26.gif' title='Map Integration' width='250' alt='Video Walkthrough of Map Feature' />
   - <img src='parse_rrs.png' title='Parse Restroom List' alt='Restrooms accounted for on Parse' />
- [x] User can see details of a restroom (opens a new screen)
   - <img src='POOP rating scn 11-26.png ' title='Rating Screen' width='250' alt='Prototype ratings screen' />
- [ ] User can see/rate/comment on the location and cleanliness of the restroom
- [x] User can filter restrooms shown (Male/Female/All Gender/No Filter)
   - <img src='POOP 12-03 settigs.gif' title='Settings/Filter screen' width='250' alt='Filter screen' />
- [ ] User can add restroom in a location

**Optional Nice-to-have Stories**

- [ ] User can see/add/resolve supply warnings (e.g. "Restroom is out of paper towels"); warnings are time-stamped and resolved in an hour if untouched (in case custodial staff resolve the issue)
- [ ] User can search restrooms

### 2. Screen Archetypes

* Map Screen
   * User can see their location and location of restrooms in their proximity
* Preferences
    * User can change filters on what restrooms are shown
* Add Restroom
    * User can add a restroom if one does not exist in the area
* Restroom Detail
   * User can see details of a selected restroom
* Rating
    * User can add details/rating/comments about a restroom

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Map Screen

**Flow Navigation** (Screen to Screen)

* Map Screen
  => Preferences
* Map Screen
  => Add Restroom
* Map Screen
  => Restroom Detail
* Restroom Detail
  => Rating

## Wireframes
<img src="TP_Wireframe.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
### Models
| **Property**   | **Type**    | **Description** |
| -------------- | ----------- | --------------- |
| location       | String      | Title of rr location|
| picture        | File        | Image of outside of restroom|
| rating         | Int         | Star rating of rr|
| ratingCount    | Int         | Amount of ratings for rr|
| updatedAt      | DateTime    | Last time rr was rated|
| comments       | Text        | Comments of rr|
| category       | String      | Male/Female/All Gender|
| status         | String      | (Stretch) whether rr needs facilities, closings (temporarily or for the day), etc|
| statusUpdatedAt| DateTime    | (Stretch) last time status was updated|
### Networking
**List of network requests by screen**
- Map Screen
   - (Read/GET) Query all restroom markers near the user
- Preferences Screen
   - (Update/PUT) Update category to filter which restrooms are shown to user
- Add Restroom
-  (Create/POST) Create a new restroom marker
   - location, picture, category (others can be added via Leave Rating Screen)  
- Restroom Detail Screen
-  (Read/GET) Query all properties
- Leave Rating Screen
-  (Create/POST) Create new rating + comment on a restroom
-  (Update/PUT) Update status of restroom
-  (Delete) Delete restroom status (i.e. resolve situation)

| **CRUD**  | **HTTP Verb** | **Example** |
| --------- | ------------- | ----------- |
| Create    | POST          | Create a new location/rating/comment|
| Read      | GET           | Get locations on map, rating/comment on rr detail pg|
| Update    | PUT           | Add user rating/comment/rr location|
| Delete    | DELETE        | Resolve rr status|
