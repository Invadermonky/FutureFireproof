# Future Fireproof Changelog
## 1.12.2-1.2.2
### Added
- Added ItemStack sensitive version of IFireproofItem#getLavaDecayRate()
- Added EntityItem sensitive version of IFireproofItem#getLavaDecayRate()
- Added IFireproofBlock, a basic interface for extending blocks to make their items fireproof
- Added implementation examples for modders on the GitHub page
### Changed
- Changed internal version string to better conform to versioning standards

---

## 1.12.2-1.2.1
- Fixed a bug cause 50 levels of fireproof enchantment to be registered
- Fireproof enchant can now be applied at high enchantment power levels (It can appear on level 30 item enchants)

---

## 1.12.2-1.2.0
- Added configuration option to make fireproof items immune to specific types of damage
- Added configuration option to make fireproof items immune to explosions
- Fixed a bug sometimes causing Fireproof items to be completely immune to damage

---

## 1.12.2-1.1.0
- Added Realistic Item Drops compatibility
- Items that extend `IFireproofItem` with a decay rate of 1 can no longer be enchanted with the Fireproof enchant

---

## 1.12.2-1.0.0
- Initial Release
