# clash-royal-analytics
## first step construct dataset with the help of cr-api (https://github.com/RoyaleAPI)

Work in progress

At the moment I write some utility classes to access the REST api and download datas:

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
