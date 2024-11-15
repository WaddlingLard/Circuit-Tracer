****************
* Project: CircuitTracer
* CS221
* 5/1/2022
* Brian Wu
**************** 

## OVERVIEW:

This program takes in files that are checked and returns them with
the best paths from points 1 to 2. There are a couple of ways the user can
interact with the program in the console.
 
## INCLUDED FILES:

* CircuitBoard.java - This class translates a readable file into code for the program (source file)
* CircuitTracer.java - This class uses a brute force search algorithm (source file pt 2)
* Storage.java - This class contains enumerations for storage types like the stack and queue
* TraceState.java - This is the class required for CircuitTracer to make its moves
* README - this file

## ANALYSIS:

Stack vs Queue is an interesting debate on what is more "effective"
and what fits more of your needs. A stack uses a LIFO system making it so
paths are searched in a depth manner. What I mean by that is the stack keeps
adding new TraceStates to each other making it only possible to access the 
most recent object on the stack. This makes the stack keep pulling out the most 
recent iteration of TraceStates and keep going from there. It is called depth searching 
where the algorithm keeps returning the most recent iteration and adding on from that.
Meanwhile, A queue uses a FIFO system making it so paths are searched in a level manner.
This means that TraceStates will be stored in a manner that can be accessed
one at a time in a continuous state, rather than be pulled out from the most 
recent addition. The algorithm goes through the queue and does a leveling search. 
This is called breadth where the program goes through all possibilities of one level and
then goes to the next.
 
When looking at the total number of search states affected by Stack or Queue, the
short answer is, no. I did the previous analysis of the simple layout and found
that both storage options would get the same amount of search states. This isn't
exactly a surprise as both are meant to go search through all routes and paths that 
are possible. 
 
Now I'm not entirely sure if this is true, but I think generally a Queue is the better
option than a Stack. Using the strategy of breadth appears to be quicker in getting
results than depth, but the simple layout analysis didn't give strong enough results. 
Thinking it out though, the principles of a breadth search seem more likely to find
a solution in a quicker time than a depth one. Let's say you are doing a depth search and
the searches are going all the way down to level 10. On the other hand, using a 
breadth search finds a solution at level 3. Generally, it can be more efficient to use 
a breadth search than a depth search, but that is more likely based on obstacles 
and the size of the board.

Using either storage solution is nice, but one great thing is the breadth search
from the queue will find the first solution with the least amount of steps. This is 
because it is going through every level it will find the solution that is sneakily
hiding from it. For example, a depth search might go around the shortest solution and
tunnel much farther than it needs. That means it then finds a solution that has 
a lot of steps and is not as efficient. A breadth search would find that solution first
and then store it before the breadth one(Usually, I think).

Looking at memory usage I don't see anything too drastic, but I think that the stack
might use less storage due to it only searching individual branches at a time.
This might not be entirely true as it could get messy with the levels and I could be wrong.
It might just be that they don't differ at all, but one thing I don't think is true is that 
queue searching uses less space. The problem with queue searching is that with each level
it searches it could almost expand in an (n^2) like manner.

The Big-Oh runtime order is pretty complicated. The amount of the times an algorithm will
run is incredibly variable from the obstacles, positioning of the points, and how big
the board is. I think that this brute force algorithm is probably an n^2 runtime. Looking at 
how the branches spread it looks like that. I think it accounts for both the number
of board positions and the number of paths because that is what we are using to find the answer.
I think it also reflects the maximum path length. Usually, the worst case would be all spots
being open and them all being populated with traces. I'm not entirely sure, but I think n
is the size of the board or the number of spaces available. Because this is a searching 
algorithm, the bigger the board/more available spots, the algorithm will have to do more work.

## COMPILING AND RUNNING:

When in the console, as you locate the folder with all the files,
compile all .java files using this command:
$ javac *.java

Hopefully, you have some .dat files as well to work with as this program
takes in a file and parses it into the program.
To run the program, you'll need to input the command following 
the given format:
$ java CircuitTracer [-q queue| -s stack] [-c console| -g GUI] filename
 
-q is when you want to use a queue for a level search (breadth)
-s is when you want to use a stack for a depth search 

-c this is for normal output in the console which is only possible as
-g isn't implemented in this program

After running the command in proper format the console will appropriately
output best paths for the file.

## PROGRAM DESIGN AND IMPORTANT CONCEPTS:

This program is a beautiful amalgamation and composition of all
sorts of classes, logic, and objects combined in one tight package. I'm
incredibly happy to say that this program works 100%, and the only thing 
that needs to be implemented for future-proofing is a GUI. The part that
I'm most proud of the format checker part of this program (It works
like a dream!).

This program can apply to many things in our world. And I'll get into that later.
The user inputs a file into the program, and then it returns all sorts of optimal
paths from two points given on the file. At the start, the input file goes
into the CircuitBoard class. This class verifies the validity of this file
to make sure the data is in check and everything required is there. It also
instantiates it in an object that the program later uses.

Once that happens, the program creates storage from a couple of enumerations in
the Storage class. Its job is to choose whether a depth or breadth search will be
used. They both have their pros and cons, but it's meant to provide flexibility to 
the user's choice.

The CircuitTracer class is where things start to get juicy. This is the class
that executes the algorithms required to find the shortest paths.
Using a CircuitBoard, the tracer class propagates the board with all sorts of 
paths using the TraceState class. It searches the board in a loop-like manner,
going down rabbit holes/levels of choice. It stores the TraceStates in the storage
allowing the program to scan each state and verify its completion. After it
finishes searching, the program outputs the most optimal paths via console/GUI output.

This program does so much and its practicality is nearly applicable to everything.
GPS, logistics, circuit boards(Crazy, Right?), Ride-sharing, CS projects(OK this one is silly) 
It's in the most basic form, but it can already do plenty, and it's complete. As I'm
typing this my grin is growing and am joyful that this is in a 100% working state.
The only thing that I would ask for improvement is to add the GUI. I feel my brain
is falling astray from java graphics and I got to get back onto that soon.

Side note: Just as a little note I know that Mason wanted us to not create any 
methods inside the CircuitBoard class, but I COULDN'T resist making a utility 
method to check characters when parsing the file. It fits in well too!

## TESTING:

Testing was useful but sometimes I ran into issues that the tester would just
stare at me with a blank face. I wouldn't be able to get feedback from the system
for the programs that I had. Don't get me wrong, the majority of issues would come 
to light from the tester, and I know, the tester isn't the be-all-end-all file. It 
was just frustrating to see a system keep saying fail when I would adjust and troubleshoot
my code. It felt like I was screaming at a computer to change when all it did was just
sit there. One solution I figured out for the opaqueness of the tester was to create 
a temporary driver class where I could see values properly outputted.

There are a couple of tests that troubled me at first but were quickly solved. They were the ones
where the commands were incorrectly entered. Also checking for invalid files was a bit of a
doozy, but I conquered it. Some tests were easy to complete like the parsing of the circuitBoard 
and throwing exceptions when needed. At a point in time, I was confused about what the valid file 
tests wanted in output, but then I saw the proper formatting on the assignment page and quickly 
figured it out. I'm almost certain this program is 100% idiot-proof and you would need some 
of the best idiots to break this one down. I haven't run into any concerning bugs and looking 
through the program the structure looks sound.

## DISCUSSION:
 
Satisfaction comes in all forms, and man was this one so strong. I felt so happy when I saw 
the testing percentages finally hit 100%. It felt so surreal to finally tread other another
obstacle that I saw as something immovable. In my first initial look at the assignment, I felt
intimidated by the size and work required to complete such as task. I felt scared that
I wasn't smart enough or proficient enough as a coder to make things work. From the previous's
project grade, I felt uneasy, uncertain that I would even get farther than 50%. Looking back,
I've never felt something so rewarding like this before(I probably have just forgotten). All the
logic, the code, It was all there. I was staring at completion and success, and it was staring 
back at me. 

I've kind of hit a "coders confidence block" while creating this project. I felt like I understood
everything, and still, I couldn't type a line. It's an over-exaggeration, but It felt like that.
Creating the format checker for the files was a breeze. I understood the assignment and used a try-catch
statement. I was able to quickly slap down exceptions and lines of code to create something that would
properly parse files. That successful feat helped me in my confidence, but the beast wasn't slain yet.

I've never run into something so unresponsive and stoic, but that might be more of my fault. 
Creating the CircuitTracer was an uphill battle. It cannot be understated how useful the logic/Sudo 
code provided on the website helped me. It made things clear and yet when I thought I was finished 
I was wrong. I thought I had completed the project, but the tester file kept outputting fails, and my 
stress built up. It took me a while to figure out why. This project is a little late and I wanted 
to complete it. I wasn't going to take no for an answer. I looked deep into the code, far down the roots
, and figured out why it was doing such a thing. It wasn't my CircuitTracer class causing this bedlam. It was
the CircuitBoard! Turns out I wasn't properly declaring/instantiating my startingPoint making it so the 
isOpen method kept outputting false negatives. It was so relieving to find the crippling mistake and bring
it to justice. 

When I said in the last project "this was the hardest thing I've ever done
in Computer Science to date" I kind of ate my own words. Maybe the double-linked list was as hard
as this, but for certain, I had to put the most effort into THIS project. 

I just want to say thank you to you, Dr. Frost. Even though I'm a little slower than I should be,
I feel like I got as much understanding from this class as from CS121. When I look back and reflect on the idea
of no collaboration being a hindrance more than I positive. I was somewhat wrong. I think separating my mind 
from other coders made it so that I would have to grow, and become strong. I understand so many ideas like
encapsulation, nodes, ADTS, testing, Big Oh, object-oriented programming, and recursion. And it's all thanks
to you. The only thing I wish for is, the textbook for CS221, man, that would've helped a lot. Also, thanks
for answering all my questions, it's been a great help.

Seriously though, thank you so much.

## EXTRA CREDIT:
Unfortunately, I didn't get to the GUI. I feel like my mind is slowly forgetting that stuff.
(Better hop to it!)

----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.
