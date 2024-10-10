# SC2002
Hospital Management System Documentation

Password :
contatins the password and methods relating to password
    password : string - for now just a string for the password
    changePassword(string newPassword) : void - method to change password
    checkPassword(string check) : boolean - returns true if the parameter is the password

User :
super class of all roles in the hospital
    password : Password - the password class for user
    role : int - number to indicate the role
    ID : int - ID of user
    name : String - name of user
    gender : int - gender of user 
    getID() : int - returns ID
    login() : int - return the role number if login is successful otherwise -1
    menu() : void - menu method for user to be overiden by subclass