module.exports = {
    displayName: "front_paciente",
    globals: {
        __PORT__: 4218,
        __STARTCMD__: "npm start -- --port 4218",
        __TIMEOUT__: 90000
    },
    preset: "../../e2eTestUtils/jest-puppeteer-utils/jest-preset.js"
};
