import java.util.*;
import java.io.*;

public class water
{
  public static void main(String[] argv)
  {
    BufferedReader in;
    StringTokenizer st;
    int setNumber;
    int drinkCount;
    int noDrink;
    int curTime;
    int curWater;
    int curDrinkNum;
    int[] drinkTime;
    int[] drinkAmount;
    boolean wasThirsty;

    boolean DEBUG = true;

    try {
      // Instantiate a BufferedReader to handle the input
      in = new BufferedReader(new FileReader("water.in"));

      // Start at set number 1
      setNumber = 1;

      // Read in the first value for the number of drinks
      drinkCount = Integer.parseInt(in.readLine().trim());

      // Loop until the fail condition is met
      while (drinkCount >= 0)
      {
        // Reset the variables
        noDrink = 0;
        curTime = 8 * 60;
        curWater = 0;
        curDrinkNum = 0;
        wasThirsty = false;
        drinkTime = new int[drinkCount];
        drinkAmount = new int[drinkCount];

        // Read in all of the input for use during the processing
        for (int i = 0; i < drinkCount; i++)
        {
          st = new StringTokenizer(in.readLine().trim(), " \t");
          int h = Integer.parseInt(st.nextToken());
          int m = Integer.parseInt(st.nextToken());
          int w = Integer.parseInt(st.nextToken());
          int s = Integer.parseInt(st.nextToken());

          // Convert the drinking time into a single minute variable
          drinkTime[i] = (h * 60) + m;

          // Determine the amount of water that can be taken in based on
          // whether it is still or sparkling
          if(s == 0)
          {
            drinkAmount[i] = 60 * Math.min(w, 4);
          }
          else
          {
            drinkAmount[i] = 60 * Math.min(w, 2);
          }
        }

        // Move past everything earlier than our current time
        for (int i = 0; i < drinkCount; i++)
        {
          if (drinkTime[i] < curTime)
          {
            curDrinkNum = i + 1;
          }
        }

        // Begin the simulation
        while (curTime < 20 * 60)
        {
          // First, see if we are still trying to drink
          if (curDrinkNum < drinkCount)
          {
            // See if we can drink right now
            if (drinkTime[curDrinkNum] == curTime)
            {
              if (DEBUG) System.out.println("Trying to drink at time = " + curTime);

              // Try to drink from the current source
              // If zero glasses of water are available, then jason cannot
              // drink, so he skips this opportunity without becoming unable
              // to drink for the next hour
              if ((noDrink <= 0) && (drinkAmount[curDrinkNum] > 0))
              {
                noDrink = 60;
                curWater = Math.max(curWater, 0);
                curWater += drinkAmount[curDrinkNum];

                if (DEBUG) System.out.println("Drinking " + drinkAmount[curDrinkNum] + "! Now have " + curWater + " minutes of water left");
              }
              else if (DEBUG) System.out.println("Jason cannot drink here!");

              // Move to the next source of water
              curDrinkNum++;
            }
          }

          // Make jason lose his water for this minute of walking
          curWater--;

          // See if jason is thirsty
          if (curWater < 0)
          {
            if (DEBUG && !wasThirsty) System.out.println("Jason is first thirsty at time = " + curTime);

            wasThirsty = true;
          }

          // Update the current time and water state
          curTime++;
          noDrink--;
        }

        // Print the results
        if (wasThirsty)
        {
          System.out.println("Data set #" + setNumber + ": Sure could use some water!");
        }
        else
        {
          System.out.println("Data set #" + setNumber + ": No problem");
        }

        // Read in the next value for the number of drinks
        drinkCount = Integer.parseInt(in.readLine().trim());
        setNumber++;

        // Print the blank line if it is needed
        if (drinkCount >= 0)
        {
          System.out.println();
        }
      }


    } catch (IOException e) {

        // Handle the exception in case there was any problem
        System.out.println("Crash: " + e);
        return;
    }
  }
}
