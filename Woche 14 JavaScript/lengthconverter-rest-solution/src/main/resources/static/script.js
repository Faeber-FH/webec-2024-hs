
const convertButton = document.getElementById('convert');
const feet = document.getElementById('feet');
const inches = document.getElementById('inches');
const result = document.getElementById('result');

feet.addEventListener('change', () => loadResult());
inches.addEventListener('change', () => loadResult());


async function loadResult() {
    try {
        const response = await fetch('http://localhost:8080/api/convert?feet=' +
            feet.value + '&inches=' + inches.value);
        if (!response.ok) throw new Error(`Response status: ${response.status}`);

        const r = await response.json();

        result.textContent = r.cmPart + 'cm, ' + r.mmPart + 'mm';
        result.className = 'success';
    } catch (e) {
        result.textContent = 'Conversion failed: ' + e.message;
        result.className = 'failure';
    }
};