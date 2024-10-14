## Future Fireproof
Simple mod that backports fireproof item logic. This mod features three ways to register an item as fireproof:
1. Configuration
   - Future Fireproof allows individual items to be recognized as fireproof and even allows users to set how fast each item decays while in lava or in fire.
  
      |   Parameter    | Required | Description                                                                                                                                      |
      |:--------------:|:--------:|:-------------------------------------------------------------------------------------------------------------------------------------------------|
      | `modid:itemid` | REQUIRED | The unique item identifier                                                                                                                       |
      |     `meta`     | OPTIONAL | The numerical item metadata                                                                                                                      |
      |  `decayRate`   | OPTIONAL | The item decay rate per tick while in lava. A value of 1 will have a normal decay rate. A value of 2 will cause the item to decay twice as fast. |


2. Enchantment
   - Future Fireproof features a Fireproof enchantment that can be applied to any item. This enchantment can be disabled in the Future Fireproof configuration.
3. IFireproofItem interface
   - This feature is intended for mod makers who wish to use this logic. Simply have your item class extend [`IFireproofItem`](https://github.com/Invadermonky/FutureFireproof/blob/master/src/main/java/com/invadermonky/futurefireproof/api/IFireproofItem.java).
