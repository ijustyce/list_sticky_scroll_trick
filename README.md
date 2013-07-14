list_sticky_scroll_trick
========================

This is a trick similar to the one exposed by Roman Nurik and Nick Butcher:
 
http://code.google.com/p/romannurik-code/source/browse/misc/scrolltricks. 

"Sticky — This trick is similar to the "synchronized scrolling" technique that +Kirill Grouchnikov described here: http://goo.gl/BHbD7. It shows how to make a scrolling view (such as a 'buy now' button) stick to the top of its scroll container instead of scrolling off-screen."

Instead of using a ScrollView, in it is modified to be used with a listView (Similar concept as Google+ uses in the profile view) instead. Also it is added the scrolling background effect included in G+. 

TODO - When performing the check of listView.getFirstVisiblePosition == 0 to get the correct scrolling position, if the scrolling is done fast enough this flag wont be valid and the sticky view will be displayed in the incorrect place. 


