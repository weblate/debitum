## 1.4.0/11
- check if viewModels in AbstractBaseListFragment should really be scoped to activity
- Add other date formats (#17)


## 1.4.1/12
- EditPersonFragment: move avatar to left of person name and update it on name change (if letter avatar)
- notify user about deleted contact and clear uri (current behaviour: hint "linked contact" shown but no name and avatar)

## 1.4.x
- rework EditTransactionViewModel to use a LiveData transaction to save user input, id=-1 indicating a new txn
- rework EditPersonViewModel to use a LiveData person to save user input, id=-1 indicating a new person

## 1.5.0/13
- add scrollbar showing the date/month/year while scrolling the transaction list
- use ACTION_CREATE_DOCUMENT / ACTION_OPEN_DOCUMENT intent to get source/destination for restore/backup (see https://github.com/lordi/tickmate/blob/master/app/src/main/java/de/smasi/tickmate/Tickmate.java)

## later / unassigned
- use RxJava (?)
- create a way to get from person sum list directly to filtered items list of a person
- make some intro showing basic functions

- improve transitions (https://material.io/blog/android-material-motion, https://developer.android.com/codelabs/material-motion-android#0)
  - UNCLEAR: how to do all this with dialog fragments???
  - fab -> new txn
  - action mode delete -> alertDialog (something basic like fade+scale or fade+slide)
  - action mode edit -> edit txn/person
  - move transition durations to integer resource



# release-checklist (1.3.2/9)
- [] check github milestone
- [x] update fastlane changelogs (2x)
- [x] update CHANGELOG.md
- [x] update screenshots
- [x] update licenses
  - ./gradlew checkLicenses
  - ./gradlew updateLicenses
  - ./gradlew generateLicensePage
- [x] check build.gradle version+version code
- [x] build release (!) apk, rename debitum-x.x.x.apk
- [x] tag release
