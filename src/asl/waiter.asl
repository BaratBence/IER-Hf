// waiter

/* Initial beliefs */

//at(P) :- pos(P,X,Y) & pos(r1,X,Y).
patrolling.
/*pos(ord,8,2).
pos(corner1,2,5).
pos(corner2,6,5).
pos(corner3,6,11).
pos(corner4,2,11).*/
patroldest(6,5).
dest(6,5).


/* Initial goal */

//!check(slots).

/* Plans */

//+!check(slots) : true <- next(slot); !check(slots).
+pos(waiter,X1,Y1) : patrolling & X1 = 2 & Y1 = 5 & patroldest(2,5)
	<-	+patroldest(6,5);
		-patroldest(2,5);
		-dest(2,5);
		+dest(6,5).
+pos(waiter,X1,Y1) : patrolling & X1 = 6 & Y1 = 5 & patroldest(6,5)
	<-	-patrolling;
		+checkingorders;
		-dest(6,5);
		+dest(8,2).
+pos(waiter,X1,Y1) : patrolling & X1 = 6 & Y1 = 11 & patroldest(6,11)
	<-	+patroldest(2,11);
		-patroldest(6,11);
		-dest(6,11);
		+dest(2,11).
+pos(waiter,X1,Y1) : patrolling & X1 = 2 & Y1 = 11 & dest(2,11)
	<-	+patroldest(2,5);
		-patroldest(2,11);
		-dest(2,11);
		+dest(2,5).
+pos(waiter,X1,Y1) : checkingorders & dest(X1,Y1)
	<-	checkOrders(arethereany);
		-checkingorders.
-checkingorders : not ordertoserve(X1,Y1)
	<-	?dest(DESTX,DESTY);
		-dest(DESTX,DESTY);
		?patroldest(PATX,PATY);
		+dest(PATX,PATY).
+pos(waiter,X1,Y1) : not ordertoserve(XX,YY) & not patrolling & not checkingorders & not dest(X1,Y1)
	<-	?patroldest(PATX,PATY);
		moveTowards(PATX,PATY).
+pos(waiter,X1,Y1) : not patrolling & not checkingorders & not ordertoserve(XX,YY) & dest(X1,Y1)
	<-	+patrolling;
		+patroldest(6,11);
		-patroldest(6,5);
		-dest(X1,Y1);
		+dest(6,11).

+dest(X1,Y1) : true
	<-	moveTowards(X1,Y1).

+pos(waiter,X1,Y1) : patrolling & not patroldest(X1,Y1)
	<-	?patroldest(DESTX,DESTY);
		moveTowards(DESTX,DESTY).
		
+pos(waiter,X1,Y1) : checkingorders & not dest(X1,Y1)
	<-	?dest(DESTX,DESTY);
		moveTowards(DESTX,DESTY).

