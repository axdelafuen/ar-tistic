classDiagram

class User{
        +Int id
        +String name
        +String profilePicture
        +String email
        +String password
        +Date birthDate
        +HashMap<User> subscribes
        +Int nbReport
    }