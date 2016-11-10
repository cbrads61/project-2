# ![](https://ga-dash.s3.amazonaws.com/production/assets/logo-9f88ae6c9c3871690e33280fcf557f33.png) Project #2: Mobile Commerce App

# FutureWarCult Vendor app for Destiny 
### *by Colin Bradley*

## App Description
- The app has and displays all information about all items offered by the FutureWarCult (FWC) vendor in Destiny

## Activity Descriptions
### **MainActivity** 
- All items offered by the vendor are located here in a RecyclerView
- Items can be searched by 3 different criteria (Name, Price cheaper than, or Type)
- Items are shown in a CardView with the picture, name, and type of the item
- All items are clickable from within the list which will open up the ItemDetailActivity showing the details of that particular item
- There is 1 button on this page that will open the ShoppingCartActivity

### **ItemDetailActivity**
- This Activity shows all information (Name, Description, Price, Image, and Type) of an individual item clicked on in the MainActivity
- All information on this page is static but has a separate layout for turning the device to landscape to re-organize the information displayed
- There is 1 button on this page that will add the item to the shopping cart list

### **ShoppingCartActivity**
- This activity shows all the items added to the shopping cart list in a RecyclerView
- The individual items have a similar layout to the items on MainActivity but they also have the price and 2 buttons on each item in the list (both an add/remove button on each item), these 2 buttons can either add or remove items from the list
- On the bottom of the page there is a view that shows the price in Legendary Marks(currency from Destiny)
- Also on the bottom there are 2 buttons(Update Cart & Checkout)
- Update Cart button will update the total price at the bottom of the cart to reflect any changes the user has made while within the cart
- Checkout button notifies user that purchase has been complete and clears out the shopping cart list

### **__Posible Bugs__**
- The total price will not update when changing items from within the cart…Update Cart button was added as a work-around to fix the price. if the user does change items in the cart but does not use the Update button, then leaves the cart, when they return to the cart the price will be updated 
- After a search is activated the list will not regenerate if the search bar is cleared…added function to recognize “all” as a search for all items in database to re-pull the list.


