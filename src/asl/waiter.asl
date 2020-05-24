// waiter

/* Initial beliefs */

patrolling.
patroldest(6,5).
dest(6,5).


/* Initial goal */

//!check(slots).

/* Plans */

+pos(waiter,X1,Y1) : patrolling & X1 = 2 & Y1 = 5 & patroldest(2,5)
	<-	+patroldest(6,5);
		-patroldest(2,5);
		-dest(2,5);
		+dest(6,5).
+pos(waiter,X1,Y1) : patrolling & X1 = 6 & Y1 = 5 & patroldest(6,5)
	<-	-patrolling;
		+patroldest(6,11);
		-patroldest(6,5);
		+checkingorders;
		+returnpoint(6,5);
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
	<-	checkOrders;
		-checkingorders.
-checkingorders : not ordertoserve(X1,Y1,SUCC)
	<-	?dest(DESTX,DESTY);
		-dest(DESTX,DESTY);
		+returning
		?returnpoint(XX,YY);
		+dest(XX,YY).
-checkingorders : ordertoserve(XX,YY,SUCC)
	<-	+serving
		?dest(DESTX,DESTY);
		-dest(DESTX,DESTY);
		+dest(XX+1,YY).
		

+pos(waiter,X1,Y1) : returning & dest(X1,Y1)
	<-	-returning;
		-returnpoint(X1,Y1);
		-dest(X1,Y1);
		?patroldest(XX,YY);
		+patrolling;
		+dest(XX,YY).

+dest(X1,Y1) : true
	<-	moveTowards(X1,Y1).

+pos(waiter,X1,Y1) : (checkingorders | takingorder | serving | returning) & not dest(X1,Y1)
	<-	?dest(XX,YY);
		moveTowards(XX,YY).
+pos(waiter,X1,Y1) : patrolling & not newcustomer & not dest(X1,Y1)
	<-	?dest(XX,YY)
		moveTowards(XX,YY).
+newcustomer : patrolling
	<-	-patrolling;
		+takingorder;
		takeOrder;
		?dest(XX,YY);
		-dest(XX,YY);
		?pos(waiter,X1,Y1);
		+returnpoint(X1,Y1);
		+dest(8,2).
+pos(waiter,X1,Y1) : takingorder & dest(X1,Y1)
	<-	?order(XX,YY);
		putOrder(XX,YY);
		-dest(X1,Y1);
		-takingorder;
		+returning;
		?returnpoint(RETX,RETY);
		+dest(RETX,RETY).
+pos(waiter,X1,Y1) : serving & dest(X1,Y1) & ordertoserve(X1-1,YY,success)
	<-	serveOrder(X1-1,YY,success);
		-ordertoserve(X1-1,YY,success);
		-serving;
		+checkingorders;
		-dest(X1,Y1);
		+dest(8,2).
+pos(waiter,X1,Y1) : serving & dest(X1,Y1) & ordertoserve(X1-1,YY,failure)
	<-	retakeOrder;
		-ordertoserve(X1-1,YY,failure);
		-serving.
-odertoserve(XX,YY,failure) : order(XX,YY)
	<-	+takingorder;
		?dest(DESTX,DESTY);
		-dest(DESTX,DESTY);
		+dest(8,2).
-ordertoserve(XX,YY,failure) : not order(XX,YY)
	<-	+checkingorders;
		?dest(DESTX,DESTY);
		-dest(DESTX,DESTY);
		+dest(8,2).
/*TODO:
	-takeOrder
	-putOrder
	-checkOrders
	-serveOrder
	-retakeOrder*/		