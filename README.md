# Job Interview

# Important

- This test is a way to evaluate experience and logical thought.
- Feel free to use of ChatGPT, Gemini, xAI, StackOverflow, google or any resource you desire.
- Third party libs as Lombok and MapStruct are not only allowed but advised.

## Description

Create an Spring Boot API for Personal Trainer Session Management

### Resources

1. [https://randomuser.me/api/](https://randomuser.me/api/)

### Requirements

1. Fetch 25 actors and 25 actresses parse them to Customers
2. Fetch 30 users parse them to Trainers
3. Fetch 50 US-States and Create one gym per state
4. CRUD Gym Sessions 
    1. Book Session 
        1. Trainer and Customer must be free to be booked
        2. All Dates should be UTC
    2. Search session by:
        1. Trainer
        2. Customer
        3. Time Interval 
    3. Cancel/Reschedule Booking
        1. Free of charge: +24h before booking start date
        2. Charge Customer: -24h before booking start date
