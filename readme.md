# Compilation Instructions

None yet I don't know  `¯\_(ツ)_/¯` To be discussed

# Coursework Assessment Brief

## 3. Command classes to be Completed
Your group task is to implement the networking code for the remaining classes below. This should include defining the protocols that handles these commands that your server must
process.

These commands are:

### 1. Get all messages (106) – Partially implemented
 (10 Marks)
### 2. Devise and implement a protocol and command(s) that allows the user to register for the
text-messaging server. Basic registration would include username and password. Full
registration can include Date-of-birth, telephone information, address details – Not
implemented
 (20 Marks)
### 3. Devise and implement a protocol and command(s) that allows the user to update
registration details – Not implemented
 (10 Marks)
### 4. Devise and implement a protocol and command(s) that allows the user to set reminders
for particular event. Users can be notified of reminders by (text message, sound alert or a
popup window) – Not implemented
 (20 Marks)
### 5. Devise and implement a protocol that allows the user to access and update reminders.
Users can be notified of reminders by (text message, sound alert or a popup window) –
Not implemented
 (10 Marks)
### 6. Extend the implementation of the text-messaging server to enable JDBC connection for at
least two command classes.
 (30 Marks)

### Error handling																																		
When you design the protocol you need to take into account error messages and
server response status codes. The functionalities for each step should be thoroughly
tested based on a studies testing strategy for software implementation.


# System Description (in base state)

## 2.3. The server
Main	server classes	are:
### MsgProtocol
The MsgProtocol class defines constants for all of the client and server command identifiers.
Use these constants in your code.
### LoginCommand and LogoutCommand
The LoginCommand and LogoutCommand classes process the login and logout
commands. You should look at these classes to see how to implement a command.
### CommandFactory
This class exists to read the command identifier sent by the client and return a command class
that can process the rest of the command. For example, if the command identifier is 101, a
LoginCommand class will be returned. Currently command classes are only implemented
for logging in and logging out.
### Handling messages – MessageCollection
The classes you need for storing messages on the server has been fully implemented. These
are called Message and MessageCollection. The Message class models the individual
messages, has sender, date and content. The date is added to a message automatically.
The send command construct a new Message object whenever a new message is sent and that
message is then added to the MessageCollection. The MessageCollection class provides a
way of holding all the messages that have been sent but not yet read.

Those messages can be accessed using the recipient name as a key. The class has all the
methods you will need for adding a new message to the collection, retrieving messages for a
particular user and finding out how many messages a particular user currently has waiting in
the collection. Note that getting a message from the collection also removes it from the
collection.
### Server connection - classes
The MsgSvrConnection class handles an individual connection between the server and a
client. It has methods to set the current user and get the current user. Another method returns
the MessageServer object because that object provides access to the message collection.
The MessageServer class is the main server class. It knows about the MessageCollection
and each MsgSvrConnection.
