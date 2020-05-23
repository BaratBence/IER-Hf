// waiter

/* Initial beliefs */

//at(P) :- pos(P,X,Y) & pos(r1,X,Y).
patrolling.
pos(ord,8,2).
pos(corner1,2,5).
pos(corner2,6,5).
pos(corner3,6,11).
pos(corner4,2,11).
dest(corner2).

/* Initial goal */

//!check(slots).

/* Plans */

//+!check(slots) : true <- next(slot); !check(slots).
+pos(waiter,X1,Y1) : patrolling & not (dest(DESTTEST) & pos(DESTTEST,X1,Y1))
	<-	?dest(DEST)
		?pos(DEST,DESTX,DESTY)
		moveTowards(DESTX,DESTY).

+pos(waiter,X1,Y1) : patrolling & X1 = 2 & Y1 = 5
	<-	-dest(corner1);
		+dest(corner2).
+pos(waiter,X1,Y1) : patrolling & X1 = 6 & Y1 = 5
	<-	-dest(corner2);
		+dest(corner3).
+pos(waiter,X1,Y1) : patrolling & X1 = 6 & Y1 = 11
	<-	-dest(corner3);
		+dest(corner4).
+pos(waiter,X1,Y1) : patrolling & X1 = 2 & Y1 = 11
	<-	-dest(corner4);
		+dest(corner3).