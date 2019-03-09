# Things I like about the codebase


# Problems/suggested improvements:
1. Test Cases are not executing correctly:
In SudokuServiceTest : @ContextConfiguration(classes = {SudokuServiceTest.Config.class,SudokuService.class}) is incorrect and it does not insert any dependency of BoardLogic class through validator method.
Instead it should be @ContextConfiguration(classes = {BoardLogic.class, SudokuService.class})

2. No need for seperate test class StaticBoardGeneratorTest. Since this test case is used to check valid boardgenerator which is present in BoardLogic class.
Sudkoservice test 
3. Instead of creating object , it could have implemented Dependency injection in SudokuService class using autowired and qualifier:
 BoardLogic boardLogic = new BoardLogic();


 


# Highest priority improvement
