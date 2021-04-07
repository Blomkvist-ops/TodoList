# Matrix TodoList

## Introduction

This is a to-do list where you can classify all your tasks to four types.
There are four types of tasks according to the level of urgency and importance. 
When you add tasks to the list, you need to choose which type this task is. 

You can describe these tasks, add tag to them, set hours needed, deadline and other details. 
This method to manage tasks is called "Eisenhower Method" and it helps me a lot when I have to deal with lots of different problems at the same time. 
So I would like to design it as an application to help more people.
  
  

You can see examples of this application in the following list.

<br/>


_**Task Types:**_
- <font color=#FF8072 >**Important and Urgent**</font>
- <font color=#6B8E23>**Important and Not Urgent**</font>
- <font color=#34D5D5>**Not Important and Urgent**</font>
- <font color=#C0C0C0>**Not Important and Not Urgent**</font>

<br/>

## User Stories

- As a user, I want to be able to add a new task to my to-do list
- As a user, I want to be able to mark a task as complete on my to-do list
- As a user, I want to be able to delete a task from my to-do-list
- As a user, I want to be able to add brief description to each task
- As a user, I want to be able to see the number of incomplete and number of completed tasks on my to-do list
- As a user, I want to be able to see the title of incomplete and completed tasks on my to-do list
- As a user, I want to be able to save my to-do list to file
- As a user, I want to be able to be able to load my to-do list from file 


Phase 4: Task 2
- I have tested and designed the Task class in my model package that is robust. The task constructor method is robust.
- I have one test for the case where the TaskTypeIncorrectException is expected and another where the exception is not expected.


Phase 4: Task 3
- I will split this class into several subclasses to increase the readability of the code.
- I will create a TodolistPanel class to deal with the interactive interface rather than put them all in the TodoListApp Class
- I will use abstract class more to reduce the code duplication
- I will use hashMap to store more todo lists, now it only has one todolist