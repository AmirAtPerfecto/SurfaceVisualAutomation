# SurfaceVisualAutomation
This project illustrates how to automate testing with Surface machines (using WinAppDriver) with a combination of visual automation and native object automation.
The test is inside testClass.java, where we
- Initiate the driver (defined in testng.xml) and reportium
- Drive the test
- Close the driver and provide the report

The two tests are:
- testEdge: essentially launch the EDGE browser by clicking on its icon (I was unable to find the right WinAppID for it).
Then we enter 'iPad' into Google search box, click on 'pro' and validate the word 'Apple' shows on the next page.
We even time the span between the search and the result.
- testCalculator is a native object example based on https://github.com/Microsoft/WinAppDriver/tree/master/Samples

To read more about how Perfecto enables test automation on Surface read more here: http://developers.perfectomobile.com/pages/viewpage.action?pageId=12419302
