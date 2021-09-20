const readline = require('readline');
console.log("Welcome to Bogota, Mr. Thomas Anderson.");
const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});


  rl.question('  >> Please enter a regEx: ', (regEx) => {
    // TODO: Log the regEx in a database
    console.log(`Thank you for your valuable feedback: ${regEx}`);
    rl.close();
  });


console.log("  >> ...");
console.log("  >> Parsing completed.");
console.log("Goodbye Mr. Anderson.");