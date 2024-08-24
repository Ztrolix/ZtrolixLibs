scoreboard players set @a VillagerCount 0
execute as @a at @s run execute as @e[type=villager,distance=..50] run execute as @a[distance=..1] run execute unless entity @e[type=villager,distance=..1] positioned ^ ^ ^1 run scoreboard players add @s VillagerCount 1
execute as @a[scores={VillagerCount=40..}] run advancement grant @s only cusadv:expmore/city