# clash-royal-analytics
## first step construct dataset with the help of cr-api (https://github.com/RoyaleAPI)

### construct utility classes to access REST cr-api:


Example:
Get top player FR
```java
			Player[] players = 
					QueryBuilder
						.selectTopPlayers(Country.FR)
						.withKeys(Arrays.asList(new String[] {"name", "tag"}))
						.withMax(5)
						.execute();
			
```

or get Player Battles

```java
			Battle[] battles = QueryBuilder
					.selectPlayerBattles(HOULALA_ID)
					.execute();

```

 ### then iterate threw battles to generate a csv file:
 
 ```java
 
 	public static void main(String args[]) throws CrServiceException {
		
		ConfigManager.init();
		
		BattleVisitor battleVisitor = new BattleVisitor();
		battleVisitor.init();
		battleVisitor.startVisit(10 * 1000); /*x * 1mn*/
		battleVisitor.end();
	}
```

Battle are initialized from top ladder players

```java
	
		Player[] players = 
				QueryBuilder
					.selectTopPlayers(Country.FR)
					.execute();
```

Then getting player last matches, and keeping opponents to get other results.
