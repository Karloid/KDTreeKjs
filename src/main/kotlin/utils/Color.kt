package utils

class Color {

    companion object {
        fun toStr(a: Float, r: Float, g: Float, b: Float): String {
            return "#" + r.toHexColor() + g.toHexColor() + b.toHexColor() + a.toHexColor()
        }

    }
}

private fun Float.toHexColor(): String {
    return (this * 255).toHex()
}

private fun Float.toHex(): String {
    val hex = this.toUInt().toString(16)
    if (hex.length == 1) {
        return "0" + hex
    }
    return hex
}
