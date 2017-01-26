# Nexus
Nexus is a MTG inspired Mod for minecraft that focuses around building and collecting mana resources of different types that generate in specific biomes. Once the connection pool is built then routing infrastructure allows the creation of ley lines for transporting the energy from all of the different mana types to a centralized location you build, the nexus point. 

##Overall Goals:	
* No Gui for most of the mod, and no “wand”, instead the user interacts with the mod directly using mouse gestures, the specific gestures are glyphs that will project from the corresponding mana crystals. (Tigram gesture inspired http://en.daoinfo.org/wiki/Finger_Gestures). Understanding not all players enjoy that sort of gesture based interaction there will also be a radial cast menu.
* Mystcraft style teleportation using leyline redirection interdimensionally and Nexus focal points for interdimensional rift creation (The rift must be formed in the target dimension, so a nexus must be created in each dimension at least once) Once the rift focus is attuned to that dimension it can then be linked to any number of other foci.
* Summoning System - Basically this would include some custom mobs that appear as translucent entites that move with you. Different abilities will allow you to summon minions and they will attack or provide benefits. A defender unit for example will automatically attempt to intercept damage, while a champion unit like the palladin of limdul will be granted bonuses against other minions etc. Minions will automatically target other minions and hostile monsters/players. They themselves cannot be targeted or aggroed directly however and players/mobs can walk right through them.
* Applied energistics style storage utilizing Mana crystal, crafting recipes, resource sinks and the mana power system. There will be a sort of channel system but instead of having actual cabling there will be linking nodes (similar to blood magic item routing system) and each routing node will allow a set amount of direct connections.
* Mana links at your base/nexus that you can “tap” to craft things, create effects, empower rituals, or use for inspiration researches
* Long term goal - Magical autocrafting requiring “upkeep”
* Leylines and Nexus have a set amount of Mana as part of the nbt data from the chunk they are generated in (randomly determined at worldgen) and recharge over time to their initial maximum. Recharging can be sped up via biome specific conditions. Green mana regen is sped up by growing plants nearby, red is sped up but fire etc, this encourages and requires interesting builds for each of your mana generators, that will incorporate with the multiblocks
* 3 tiers of multiblock mana pool first tier just a hole in the ground mana collects then a 5x5 (think Ars magica crafting altar) and final tier is a 13x13 structure
* A nexus is formed once all the mana colors are attuned to an inert crystal (A crystal placed in the world and then the linking gesture used from each mana source to the crystal). If a nexus is drained too low (<10%) it will start tearing up the land in a desperate attempt to sustain itself (Similar to a hungry node).
* If a nexus is completely drained it will become inert and need to be restored.
* Spell discovery via inspiration research
* Magic system that allows the player to bend, and break the laws of physics using the magic that connects and binds reality itself to obtain direct manipulation of the weave. Spells are cast by direct gesture OR by selecting the gesture from a radial menu and activating the keybind. 
* There should be around 20~30 spell effects but each player can only “equip” 7 at a time, reflective of the MTG need to strategize and limit your hand.
* To use the spells on a personal level you will need mana to fuel them, you can infuse up to 50 “buckets” of mana, the colors can be mixed to your liking. For instance if I want to focus on growth and fire (Green/red) magic I can infuse myself with 25 green and 25 red equally which would power the glyph’s I have prepared. 
* Rifts - Tears in reality - High end portion of the game, easier rifts can be created for just teleportation with difficulty similar to that of mystcraft, a single rift can also be linked to multiple rifts in this way to cycle through. A rift linked to multiple rifts will cycle its destination once every 2 seconds displaying a glyph representation of its target (or better an actual image similar to some CCTV mods, if this could be done without chunk loading every destination point) a rift stabilizer will allow you to lock onto a destination once a redstone signal is supplied. CC/OC integration here would be cool.
* Lots of fun PVP spells/effects and systems
* Focus on high utility and working with other mods
* Dimensional Folds - these are similar to rifts but the can’t link to multiple targets, each fold comes in a multiblock pair, this would allow for transdimensional transmission of anything passing through, the multiblock itself would allow for expandability of the rift to put more cabling/conduits/people etc through the rift. It would need to be the same size structure on both sides (ie 4x5 or w/e, 1x1 being the smallest) before it can be initialized. Dimensional Folds would support one to many connections so essentially our take on a tesseract.
* Needs ui display element for current/maximum mana capacities as well as your prepared spells


## MTG Inspired player abilities, abilities will be tier gated (will require balance work):
* Defender, While blocking player absorbs ALL damage to any non hostile target in a 9x9 area
* Animate dead - brings the dead back to life, players or mobs
* First Strike - Anytime you would be attacked you automatically hit first (10s cooldown)
* Flying - has mana upkeep cost, can be sustained via mana pools in nearby chunks if available
* Hexproof - toggle ability, constant upkeep, automatically purges negative effects on you while active.
* Indestructible - While active your armor and equipment cannot take damage
* Lifelink - You attune your life essence to a white crystal that you give to another player, any damage you take will be split with them, any healing you or regeneration receive will also apply to them. Removing the crystal from their inventory will break the link.
* Protection - Grants resistance buff (vanilla) for a duration based on how much you empower it
* Reach - allows you to extend your attack and interaction range 
* Trample - all of your attacks have a trample effect, if the primary target is killed any remaining damage is split among all hostile targets in a 3x3 area.
* Alacrity - Allows you to  cast faster, has a multicolor upkeep cost
* Rampage - +1 damage dealt for every enemy near you (3x3 area) and +1 armor level.
* Phasing - allows you to enter a noclip mode, cant attack or be attacked, lasts for a short duration
* Echo - this is a spell you’d typically cast ahead of time, it will cause your next spell effect to be immediately echoed, casting it twice with no cast time
* Provoke - basically a taunt, all mobs in the area will set you as their ai target
* Bloodthirst - gain +1 damage and +1 armor for every monster you kill, this bonus degrades after not killing anything for a time. The speed it degrades increases exponentially with the bonus so obtaining anything more than 5~7 will be next to impossible without constantly aoe killing hordes.
* Counterspell - instant cast, will cancel your target’s spell, will also dispel them
* Lightning Bolt - causes a lightning strike where you’re looking
* Fireball - causes a ghast like fireball to shoot from you (bigger fire explosion, less terrain damage)
* Vanish - Invisibility, lets hide armor too because ffs.
* Absorb - gives 2~6 absorption hearts depending on empowerment level
* Champion - temporarily gives a full bonus health bar, strength 2 and resistance 2 at a large mana cost
* Cascade - the next spell you cast will bounce to up to 3 nearby targets
* Living weapon - a flying sword appears to fight as your ally
* Undying  - If you would have died from damage this spell is instead dispelled and you are returned to life with resistance and 2 absorption hearts.
* Miracle - this is an upkeep cost spell, rng dependant, it will randomly alert you that you’ve been bestowed a miracle and your next cast will be completely free.
* Dash - Instant cast ability, super accelerates you a short distance where you’re looking and gives 3 seconds of haste upon arriving, low cost, spammable. It also resets velocity so if  you dash before hitting the ground it should prevent fall damage.
* Shield - allows you to absorb all damage at the cost of mana
* Swords to Plowshares - kill target non boss creature, its life is divided and added to the health pool of all other mobs in the area.
* Assassinate - allows you to instantly kill any creature with less than half your health.
* Reflect - reflects the next spell cast against you back to its caster. If both casters have reflect active this is nullified and the reflects are not consumed.
* Regeneration - provides regeneration potion effect, level and duration scale with the amount of empowerment.
* Fabricate - allows you to create basic materials (vanilla materials) with a mana cost. You hold the item you want and then charge the spell until enough of the appropriate mana types have been focused then it will create.
* Control - hostile enemies may be controlled turning them into allies that fight for you.
* Tap - bestows the Tapped effect, prevents casting or attacking for 3 seconds.
* Mana link - allows you to share mana with another player/players.
* Trance - you cannot move but all of your resistance are increased and your spells my channel twice as long
* Transmogrify - chirps the word and you’re a bird. Turns enemies into a random farm animal. (doesn’t work on players).
* Soullink - spells you cast will also be cast by the person holding your lifelink crystal at 50% increased cost. The target will be determined based on where they are looking as well as you for your own casts. 
* Bestow - allows you to transfer the ability for anyone to cast a single spell that you have prepared. Granting access to the spell will not grant them any progression and you pre-pay the mana cost for as many times as you wish them to be able to cast it. If you wanted to give someone the dash ability for instance you first cast bestow and then cast dash on them a number of times, maximum amount of casts is limited by mana, not amount of casts so a low mana spell can be bestow more uses.
* Rifting - allows you to create a mystcraft style portal that will fade after a short time to any Nexus you have previously accessed (radial menu will list them) While the portal is open (For about 10 seconds) anyone may pass through with you.
* Angelijah ~ Celestial weapon - Works with the Focusing pedestal, place weapon of your choice in the pedestal and when you cast this spell a glowing shadow copy of this weapon appears in your hand, in addition to the weapon properties and enchants the celestial weapon will additionally have smite and strike through, hitting up to 3 blocks in front of you (all targets), all weapon damage is applied to the celestial weapon instead of the actual weapon and the celestial weapon has a 20% chance per attack to phase through all defenses bypassing blocking and armor. 
* Landwalking - When active gain powers according to the biome activated in, powers don’t update as you change biomes unless you recast. Mana cost of opposing color spells doubled while active. 
* Plains type - Fast Move, small chance of bonemeal effect around you
* Hills/Mountains - Feather Fall+Double jump
* Swamp - Not slowed by quicksand/soulsand/oil
* Ocean - water breathing, aqua affinity
* Forest - Barkskin (bonus armor), periodic regen 1 cast on you
* Void - Immunity to void damage

## MTG Themed items:
* Abzan Banner - placeable decorative item, 30min cooldown, will randomly grant an additional prepared spell slot for 30 minutes, the spell cannot be chosen it’s random.
* Dimir Keyrune - when active gain bonus black and blue mana, appears as a blueish orb that will attack enemies or assist you in mining for the next minute until returning to you as an item.
* Engineered explosive - destroys a cascade of blocks that are identical to the targeted block, returns some mana of the type of biome you are in and depletes the overall mana of that chunk, once mana is depleted below 10% of the chunk’s max it’s no longer usable in that chunk.
* Everflowing chalice - grants 1 charge every time you are attacked, at 10 charges it becomes filled and may be used as an alabaster potion.
* Keening Stone - worn as ring bauble, allows you to periodically see flashes of ores or chests through the ground.
* Khalni Gemmed Ring - whenever someone casts a spell around you they must be able to afford 2 additional mana cost or the spell fails, you gain this additional mana if they pay. Will slowly generate absorption hearts as you cast spells.
* Lion’s eye diamond ring - add +1 to your maximum mana pool for all colors, allows you to see the amount and mana type in a chunk
* Pentagram of the ages - When activated absorbs ALL damage dealt to you for the next 10 seconds. After 10 seconds the pentagram will detonate with a radius and damage that scales based on how much it absorbed. This item is destroyed in the process.
* Pristine wraps - belt, add +1 mana pool, and +2 hearts to your maximum health pool. Increases movement speed. 
* Proteus staff - this staff is receptive of magic, you may cast a spell upon it after placing it in the world as a block, the proteus staff will continue to cast the spell once per second as long as it has mana when given a readstone.
* Black lotus - grows extremely rarely in worldgen, grants 3 colorless mana when used. Can be grown with the right conditions, slow growth time (like ender lillies on dirt)
* Soul harvest Gem - collects souls of slain enemies to use as colorless mana.
* Jayemdae Tome - 30 second cooldown, will randomly cast a spell, even ones you dont have prepared or unlocked at the cost of 5 colorless mana.
* Phyrexian Processor - each time you right click it you take 1 damage, when you right click on the ground Phyrexian Processor spawns a shadow minion with life equal to the amount you sacrificed, its damage and armor also scale with the amount sacrificed. The Shadow minion can fly and fights on your behalf until dead. Activating the processor costs 4 black mana and two colorless.
* Nevinyrrals Disk: becomes inert after use and must be recharged, destroys all enemies in the entire chunk instantly, sets everyone’s mana to 0 and completely dispels everyone in the chunk. Must be placed as a block and takes 5 seconds to activate. 
* Sol Ring - increase all mana pools by 2, has a chance to consume harmful effects and convert them to mana. 
* Mox Gems - one of each color type, while in your inventory they permanently increase your mana of the corresponding type by 1, at the cost of an inventory slot.
* Null Rod - all baubles fail to function in the radius when active.
* Spectacles of the white - You may use white mana in place of any color mana for casting
* Swiftfoot sash - grants movement speed and dispels movement impairing effects automatically (has mana upkeep cost).
* Emerald Medallion - neck bauble, your spells cost 1 less mana to cast
* Alabaster’s Potion - temporary potions (they vanish after a while)

Instantly restores 3-4 hearts

Grants regeneration



## <b>Research system:</b>
Learn by doing/experimentation similar to ars magica, mix and match combinations of elemental summons which are uncovered via exploration and trading with other players/villages. Primal Inspirations are color based white, black, red, blue, green. Using a combination of these runes (Up to 4) the player can design spells that are powered with Mana of the corresponding types and amounts. The amount of power put into a spell on cast (by channeling with right click after the gesture) determines magnitude, damage, duration and invokes extra effects depending on the spell, a full write up of intended spells and combinations will follow in the future as well as proper naming.


##<b>Worldgen:</b>
Randomly generated nbt data for chunks determining the maximum mana that it may contain. High mana values can create more (higher capacity) but generate more slowly, so there is a choice and tradeoff to be made by the player for power regeneration vs maximum capacity. Each biome will have a very low chance of a randomly generated mana pool, visiting these naturally occurring mana pools will trigger inspiration for that type of primal mana allowing the users to begin working with them. The worldgen pools are used in the research system as each provides an inspiration for its corresponding type. The inspiration can also be written down in a book for crafting or can be traded. ie If I travel for a few thousand meters and collect 4 Black inspirations, Red Inspirations and a White Inspiration I can inscribe one of my inspirations into a condensed orb at a small mana cost and trade that to another player. I lose one of my stored inspirations in the process but will gain the one they trade me after using the orb they give me. At high progression you begin to have a chance to inscribe inspirations without losing your own, but to prevent hoarding you can only have a maximum of 4 of each type at any given time. Mana pools should be no closer together than 2 chunks an attune to a mana pool that is too close to another will give an error and disallow linking.Near mana sources a lotus of the appropriate time will sometimes grow. This lotus can be harvested with shears and planted/cultivated for crafting components. Muticolor mana nodes will need to be setup on chunk’s bordering two appropriate biomes. If this proves to difficulty to find there is alternaives farther in the progression path utilizing colorless mana from the void (end) which can be shifted with some of the mod blocks. 
