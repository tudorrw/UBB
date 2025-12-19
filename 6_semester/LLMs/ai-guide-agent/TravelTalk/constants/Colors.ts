/**
 * Below are the colors that are used in the app. The colors are defined in the light and dark mode.
 * There are many other ways to style your app. For example, [Nativewind](https://www.nativewind.dev/), [Tamagui](https://tamagui.dev/), [unistyles](https://reactnativeunistyles.vercel.app), etc.
 */

const tintColorLight = '#0a7ea4';
const tintColorDark = '#fff';

export const Colors = {
  light: {
    text: '#11181C',
    background: '#fff',
    tint: tintColorLight,
    icon: '#687076',
    tabIconDefault: '#687076',
    tabIconSelected: tintColorLight,
  },
  dark: {
    text: '#ECEDEE',
    background: '#151718',
    tint: tintColorDark,
    icon: '#9BA1A6',
    tabIconDefault: '#9BA1A6',
    tabIconSelected: tintColorDark,
  },
};


export const ColorPalette = {
  primary: '#20AB6E',  // Keep primary green for consistency
  lime: '#D4FFCC',     // Softer lime for a refreshing nature vibe
  pink: '#F77FEF',     // Lighter pink for a playful touch
  brown: '#4B3621',    // Warmer brown for earthy elements
  sky: '#B3E5FC',      // Light sky blue, echoing open skies
  teal: '#00796B',     // Vibrant teal for energy
  yellow: '#FFD54F',   // Brightened yellow for warmth
  orange: '#FF7043',   // Softer orange for sunset tones
  blue: '#1E88E5',     // Calmer blue for water and sky themes
  green: '#2E7D32',    // Rich forest green, evoking nature
  light: '#FFFFFF',    // Pure white for balance
  grey: '#424242',     // Dark grey for subtle contrasts
  greyLight: '#B0BEC5', // Neutral light grey for backgrounds
  input: '#E0F7FA',    // Soft aqua for input fields, invoking water
  selected: '#FFF8E1', // Warm cream for selection highlights
  dark: '#212121',     // Deep grey for night scenes
};
