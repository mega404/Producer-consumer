# Producer-consumer
## Description
An assembly line that produces different products consists of different processing machines
Ms that are responsible for processing the product at different stages and queue Qs to handle
product movement between different processing stages (see figure below). In this assignment
we developed a simulation program to simulate this production line as a queuing network.

## Assumptions 
- Any queue is only joined to a machine
- Any machine is only joined to a queue
- The number of objects in the queue is typed bellow the queue number
- If the user wants to join two objects he select the first object then the second object and presses the join button
- Number of objects at the start of the simulation is 9
## Design Patterns used
1. Concurrency design pattern: <br />
This design pattern is applied in making machines -
Threads- that consume objects from certain queues for a
certain time then produce them.
2. Observer design pattern : <br />
This design pattern is used to observe whether one of
certain queues for each machine has elements to consume
or wait for them to have elements.
3. SnapShot design pattern : <br />
This design pattern is used in making snaps to store
certain data when a machine consumes or produces an
object so the simulation can be replayed.
4. Marker interface design pattern : <br />
Machine class implements runnable interface to be able
to make threads.


## User Guide
- The main page of the prodcer-consumer program
![main](/assets/main.png)

- To generate a machine just press the machine button
![machine](/assets/machine.png)

- To generate a queue just press the queue button
![machine](/assets/queue.png)

- To join to objects select the first object then the second
object then press the join button
![machine](/assets/join.png)

- Here is a full assembly line
![machine](/assets/full%20example.png)

### Description of the program running
Draw the shape that you want to simulate then press the
simulate button The UI shows the simulation by displaying the number of
elements in the Qs in real time.
- Ms flash when they finish servicing an item and every
product has its own color (a random color) that will keep it
from start till the end and each machine will change its
color the product’s color being processed by it then change
back to a default color once done to make following the
simulation easy for the user.
- After the simulation ends, the user can replay the
previous simulation by pressing replay
- If the pressed stop then the simulation ends , and the user
can replay the previous simulation by pressing replay till
the stop pressed
or start a new simulation by pressing new


### How to Run
1. Run the server of the front-end at port “4200” for example
Type “ng s” in your cmd after choosing the project
directory.
2. Run the server of the back-end at port “8080”
3. Type in your browser “http://localhost:4200”
4. Start using your Producer_consumer application





## Video
Video of the program running [here](https://drive.google.com/drive/folders/1oeWC_SnP28UHXUPrZU68hazPOPkVF6dr?usp=sharing)

## UML Diagram
You can find it [here](https://drive.google.com/file/d/1sp9ZMcfDNTYQxKjXdQcMTBMsy5Mm-dZC/view?usp=sharing)

## Team 
- Abdelrahman Ibrahim
- Abdelrahman Yousry
- Salah Eltenehy
- Mahmoud Ebrahim
