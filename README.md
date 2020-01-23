# KotlinPOC

This is a POC Android application using Kotlin. I have used MVVM design pattern to develop this. Below are some main libraries/Components used for development

- Retrofit
- LiveData
- ViewModel
- Picasso
- Mockito
- Espresso


### I have written some unit test case. Below are the details:
- Unit test cases for ViewModel - 100% coverage
- Utility class - 50% method coverage(Other method is Android specific)

### I have also written some Andorid's UI tests. Below are the details:
- Test case for testing that app loads and show all data from the API response
- Test case to test when network is not available then an error message is shown. (This test will only pass if only WiFi is been used while testing, as this test only disconnects from WiFi.)


### Known issues
- Some list item don't show image and only place-holder image is shown
    
    Reason: There is no image url available OR some url loads but there is not successful response
