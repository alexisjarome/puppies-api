**puppies-api Exercise on creating APIs using spring boot**

Pre-requisites:
Maven 
Jdk 8 or above



To run the application (After pulling/cloning the repo):

1.) In command line, execute **mvn spring-boot:run**

2.) In STS or Eclipse IDE, go to com.assignment.puppies.PuppiesApiApplication.java and run as Spring boot app

default port will be 8080


**List of APIs**

**NOTE:** APIs 3,4,5 and 8 needs Authorization header with value being the token from the Authenticate API) 

Example: 'Authorization: Bearer brockdotceraatpuppiesdotcom'


1. **(POST) Register** - http://localhost:8080/register

   Sample body : {
  "firstName": "Brock",
  "lastName": "Cera",
  "email": "brock.cera@puppies.com",
  "password": "brocker123"
  }

   Sample Response : {
    "id": 9,
    "firstName": "Brock",
    "lastName": "Cera",
    "email": "brock.cera@puppies.com",
    "name": "Brock Cera"
}


2. **(POST) Authenticate**  - http://localhost:8080/authenticate

   Sample body : {"email":"brock.cera@puppies.com", "password":"brocker123"}

   Sample Response : { "token": "brockdotceraatpuppiesdotcom" }


3. **(POST) Create a Post** - http://localhost:8080/post

   Sample body: {
  "text": "My 2nd post",
  "imageName": "cuter-puppy.png"
}

   Sample Response: {
    "id": 10,
    "text": "My 2nd post",
    "imageName": "cuter-puppy.png",
    "base64Image": null,
    "date": "2022-01-20T14:41:45.130+00:00",
    "like": false,
    "user": {
        "id": 4,
        "firstName": "Jane",
        "lastName": "Raz",
        "email": "jane.raz@puppies.com",
        "name": "Jane Raz"
    }
}


4. **(POST) Like a Post** - http://localhost:8080/post/like/{postId}

    Sample body: n/a

    Sample response: {
    "message": "success"
}


5. **(GET) Fetch a User's feed** - http://localhost:8080/feed

    Sample body: n/a

    Sample response: [
    {
        "id": 10,
        "text": "My 2nd post",
        "imageName": "cuter-puppy.png",
        "base64Image": null,
        "date": "2022-01-20T14:41:45.130+00:00",
        "like": true,
        "user": {
            "id": 4,
            "firstName": "Jane",
            "lastName": "Raz",
            "email": "jane.raz@puppies.com",
            "name": "Jane Raz"
        }
    },
    {
        "id": 6,
        "text": "Bob's Puppy",
        "imageName": "bob-puppy.jpg",
        "base64Image": null,
        "date": "2022-01-20T14:40:56.953+00:00",
        "like": false,
        "user": {
            "id": 2,
            "firstName": "Bob",
            "lastName": "Laude",
            "email": "boblaude@puppies.com",
            "name": "Bob Laude"
        }
    }]


6. **(GET) Fetch details of an individual post** - http://localhost:8080/posts/{postId}

    Sample body: n/a
    
    Sample response: {
    "id": 8,
    "text": "Jane's puppy",
    "imageName": "jane-puppy.jpg",
    "base64Image": null,
    "date": "2022-01-20T14:40:56.953+00:00",
    "like": false,
    "user": {
        "id": 4,
        "firstName": "Jane",
        "lastName": "Raz",
        "email": "jane.raz@puppies.com",
        "name": "Jane Raz"
    }
}


7.  **(GET) Fetch a user’s profile** - http://localhost:8080/users/{userId}

    Sample body: n/a

    Sample response: {
    "id": 1,
    "firstName": "Mark",
    "lastName": "Klein",
    "email": "mark.klein@puppies.com",
    "name": "Mark Klein"
}


8.  **(GET) Fetch a list of the user’s liked posts** - http://localhost:8080/likedPosts

    Sample body: n/a

    Sample response: [
    {
        "id": 10,
        "text": "My 2nd post",
        "imageName": "cuter-puppy.png",
        "base64Image": null,
        "date": "2022-01-20T14:41:45.130+00:00",
        "like": true,
        "user": {
            "id": 4,
            "firstName": "Jane",
            "lastName": "Raz",
            "email": "jane.raz@puppies.com",
            "name": "Jane Raz"
        }
    }
]


9.  **(GET) Fetch a list of posts the user made** http://localhost:8080/users/{used/posts

    Sample body: n/a

    Sample response: [
    {
        "id": 8,
        "text": "Jane's puppy",
        "imageName": "jane-puppy.jpg",
        "base64Image": null,
        "date": "2022-01-20T14:40:56.953+00:00",
        "like": false,
        "user": {
            "id": 4,
            "firstName": "Jane",
            "lastName": "Raz",
            "email": "jane.raz@puppies.com",
            "name": "Jane Raz"
        }
    },
    {
        "id": 10,
        "text": "My 2nd post",
        "imageName": "cuter-puppy.png",
        "base64Image": null,
        "date": "2022-01-20T14:41:45.130+00:00",
        "like": false,
        "user": {
            "id": 4,
            "firstName": "Jane",
            "lastName": "Raz",
            "email": "jane.raz@puppies.com",
            "name": "Jane Raz"
        }
    }
]



**APIs can be tested in Postman - please import file Puppies API.postman_collection.json**




    

