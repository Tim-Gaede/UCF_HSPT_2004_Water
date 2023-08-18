#include <iostream>
#include <fstream> 
using namespace std;

//Convert from hours:minutes from midnight to minutes from midnight.
int convert( int hours, int minutes ) {
	return hours * 60 + minutes;
}

//Get the minimum of two integers.
int min( int x, int y ) {
	//This is the ?: operator.
	//If x < y, the program will use x as the return value.
	//Otherwise, it will use y.
	return ( x < y ) ? x : y;
}

int main() {
	ifstream input( "water.in" );

	//The index of the current run.
	int curRunI = 1;
	//The number of times Jason attempts a drink in this run.
	int numDrinks;

	input >> numDrinks;

	while( numDrinks >= 0 ) {
		//Pretend that Jason's first drink of the day was at 0700. 
		//Thus, Jason needs a drink at 0800, as per the specifications.
		int lastTime = convert( 8, 0 ) - 60;
		int timeRemaining = 60;
		
		//Tells whether to just skip over input lines
		//The program does this to skip over the end of a data set
		//after it has determined that Jason will go thirsty.
		bool skipInputLines = false;

		for( int drinkI = 0; drinkI < numDrinks; drinkI++ ) {
			//Input: hours:muntes since midnight
			//numDrinksAvai: number of drinks available at stop.
			//sparklingFlag: whether or not the water is 
			//sparkling
			int hours, minutes, numDrinksAvail, sparklingFlag;

			input >> hours >> minutes >> numDrinksAvail
			 >> sparklingFlag;

			 if( skipInputLines ) {
				 continue;
			 }

			//Check the sparkling flag.
			bool sparkling = ( 0 != sparklingFlag );

			//Time of the current drink.
			int currentTime = convert( hours, minutes ) ;

			//Jason will not drink before 0800 or after 2000.
			//Jason will not even be walking at these times!
			//Thus ignore any lines refering to them.
			if( currentTime < convert( 8, 0 ) 
				|| currentTime > convert( 20, 0 ) ) {
				continue;
			}

			//If there is no water offered at a location, ignore this 
			//case
			if( numDrinksAvail <= 0 ) {
				continue;
			}

			if( currentTime - lastTime >= 60 ) {
				//The number of drinks of water Jason managed
				//to get down.
				int numDrinksDrunk;

				timeRemaining -= ( currentTime - lastTime );
				//If Jason became thirsty, stop.
				if( timeRemaining < 0 ) {
					skipInputLines = true;
				}

				if( sparkling ) {
					numDrinksDrunk = min( 2, numDrinksAvail);
				} else {
					numDrinksDrunk = min( 4, numDrinksAvail);
				}
				//If there is no water offered at a location, ignore this 
				//case
				
				//Jason drinks the water.
				if (!skipInputLines)
					timeRemaining += 60 * numDrinksDrunk;

				lastTime = currentTime;

			}
		} //end for drinkI
		
		//See if Jason can last untill 2000!
		timeRemaining -= ( convert( 20, 00 ) - lastTime );

		cout << "Data Set #" << curRunI << ": ";
		if( timeRemaining >= 0 ) {
			cout << "No problem" << endl;
		} else {
			cout << "Sure could use some water!" << endl;
		}
		cout << endl;
		
		input >> numDrinks;
		curRunI++;
	} //end while numDrinks

	return 0;
}
