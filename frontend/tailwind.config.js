module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  darkMode: 'class', // Enables dark mode via a CSS class
  theme: {
    extend: {
      colors: {
        primary: '#1E40AF',    // Example primary color
        secondary: '#F59E0B',  // Example secondary color
        'secondary-dark': '#D97706', // Dark variant for hover
        'neonGreen': '#7FFF00'
      },
    },

    
  },
  plugins: [],
}
