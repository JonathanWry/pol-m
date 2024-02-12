# ask for confirmation to run mvn.sh
read -p "Do you want to recompile the source code? (y/n)" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "Running mvn.sh"
    cd ..
    sh mvn.sh
    cd run
else
    echo "runing the previous version of the code..."
fi

cd experiment
sh run.sh
cd ..
