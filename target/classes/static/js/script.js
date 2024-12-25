console.log("Script loaded");

let currentTheme = getTheme();
// Set the initial theme
changeTheme();

function changeTheme(){
    // Set the initial theme to the webpage
    document.querySelector("html").classList.add(currentTheme);

    // Set the listener to change the theme when the button is clicked
    const changeThemeButton = document.querySelector('#theme-change-button');

    // Initially set the button text based on the current theme
    updateButtonText(changeThemeButton);

    // Change the theme on button click
    changeThemeButton.addEventListener('click', () => {
        console.log("change theme button clicked");

        // Toggle the theme
        if (currentTheme === "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";
        }

        // Update the theme in localStorage
        setTheme(currentTheme);

        // Remove the current theme class and add the new theme class
        document.querySelector('html').classList.remove("dark", "light");
        document.querySelector('html').classList.add(currentTheme);

        // Update the button text based on the new theme
        updateButtonText(changeThemeButton);
    });
}

// Function to update the button text based on the current theme
function updateButtonText(button) {
    button.querySelector("span").textContent = currentTheme === "light" ? "Dark" : "Light";
}

// Set theme in localStorage
function setTheme(theme){
    localStorage.setItem("theme", theme);
}

// Get theme from localStorage, default to 'light' if not set
function getTheme() {
    let theme = localStorage.getItem("theme");
    if (theme) {
        return theme;
    } else {
        return "light"; // default theme
    }
}
