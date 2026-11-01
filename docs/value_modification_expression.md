# Value Modification Expression

## Basic Expression

Basic expressions can be 4 patterns:

1. `~`: do nothing, the original value is kept as-is. (not support attributes!)
2. `=100`: replace the original value to a fixed value.
3. `+50` or `-20`: add or subtract.
4. `*1.5`: multiply

### Examples

#### 1 tick recipes

`S:generalModification==1{min=1}`

The expression is `=1{min=1}`; attribute `{min=1}` is used to ensure that the duration won't be less than 1 tick, or
it's likely causing error.

#### 100 percent water purification

`S:waterPurificationPlantSuccessChance==100`

## Attributes

Attributes are used to correct the modified value, to ensure that the value is valid.

`~` doesn't support attributes, so use something like `+0{max=100}` or `*1{min=1}`.

### Min and Max

`{min=1}` means the value won't be less than 1.
`{max=100}` means the value won't be greater than 100.
