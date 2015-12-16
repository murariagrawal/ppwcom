// Place third party dependencies in the lib folder
//
// Configure loading modules from the lib directory,
// except 'app' ones, 
requirejs.config({
    "baseUrl": "js/vendor",
    "paths": {
      "user": "../user",
      "common":"../common",
      "jquery": "jquery.min"
    }
});

// Load the main app module to start the app
requirejs(["user/steps"]);