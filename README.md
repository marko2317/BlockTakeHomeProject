# Employee Directory Sample Project
Implementation of the Employee Directory Mobile Take-Home Project for Block.

## Build tools & versions used
activityx-ktx 1.8.0
orbit-viewmodel 4.3.2
retrofit 2.9.0
converter-gson 2.9.0
kotlin-coroutines-adapter 0.9.2
coil 2.1.0
mockito 4.6.1

## Steps to run the app
Click the Run button in Android Studio

## What areas of the app did you focus on?
My main focus was having a clean architecture for the project. I decided to use Orbit MVI as the
main architecture.

## What was the reason for your focus? What problems were you trying to solve?
Recently I started working with the architecture and I wanted to learn more about it and this project
seems to be a good fit to test my knowledge with it. 

The main problem I was trying to solve is having a clean, scalable and maintainable architecture that
is future prove. 

## How long did you spend on this project?
~5 hours

## Did you make any trade-offs for this project? What would you have done differently with more time?
I wanted to add Dependency Injection but didn't had the time to do so. Probably I would include Dagger
or Hilt.
I also didn't implemented the Image caching support within Coil. 

## What do you think is the weakest part of your project?
The UI design

## Did you copy any code or dependencies? Please make sure to attribute them here!
Handling errors with Retrofit and Coroutines - https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
Add Interceptor for Testing - https://medium.com/ki-labs-engineering/an-easy-way-to-mock-an-api-response-using-retrofit-okhttp-and-interceptor-7968e1f0d050

## Is there any other information you’d like us to know?
I added UI states for the malformed and empty endpoints. This can be accessed via the options menu
The refresh action in the toolbar queries the employees.json endpoint