# Google Image Search
This is an app completed for the CodePath Android Bootcamp Class.

This is an application you can use to search the web for images based on a search string.  You can optionally add filters to better define your search and return particular image types.  Once you found an image you like, you're able to save the image to the device, share it, or view the web page containing the image.

Time Spent: ~30-40 Hours

## User Stories
### Required User Stories
* [x] User can enter a search query that will display a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of advanced search options to filter results
* [x] User can configure advanced search filters.  (Image Size, Dominant Color, Color Type, Image Type, Safe Search)
* [x] Subsequent searches will have any filters applied to the search results
* [x] User can tap on any image in results to see the image full-screen
* [x] User can scroll down “infinitely” to continue loading more image results (up to 10 pages) 

### Optional User Stories
* [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures (Mostly)
* [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* [x] Advanced: User can share an image to their friends or email it to themselves
* [x] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
* [x] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
* [x] Bonus: Use the StaggeredGridView to display improve the grid of image results
* [x] Bonus: User can zoom or pan images displayed in full-screen detail view

### Additional User Stories
* [x] Can select the number of columns you wish to have in your view
* [x] As images load they first load an on-device placeholder, then the small thumbnail url, and finally full sized image in order
* [x] Can save the image to the device gallery
* [x] Can open the webpage the image comes from
* [x] Used the new Google Custom Search Engine API over the depricated API

# User Story Walkthrough
![Video Walkthrough](WalkthroughV1.gif)

# Known Bugs:
* Settings Dialog doesn't look good in landscape on small devices.  Need to make a new dialog for different sizes to handle this
* It reloads all items as items change (due to the notifyDataSetChanged being called)
* If you scroll too fast you get Out of Memory issues in Picasso as it tries to load too many images at once

# Wants
* I used the new Google Search Engine with hopes that I could do a reverse image search to find similar images using an image aquired from the camera or from an image located on the device
* Better Performance so we don't see the placeholders as we scroll up/down
* The Custom Google Search Engine does not allow you to search by site unless you set it up to ONLY search by a single site.

# Acknowledgements
* Lots of assistance via [Codepath Tutorals](http://guides.codepath.com/android/)
* Async Data Requests made using [Android Asynchronous HTTP Client](http://loopj.com/android-async-http/)
* Image loading and management using [Picasso](https://square.github.io/picasso/)
* Staggered GridView and Dynamic Height ImageViewsusing [AndroidStaggeredGrid](https://github.com/f-barth/AndroidStaggeredGrid)
* Touch Responsive ImageViews with Zooming by [TouchImageView](https://github.com/MikeOrtiz/TouchImageView)
* GIF created with [LiceCap](http://www.cockos.com/licecap/).