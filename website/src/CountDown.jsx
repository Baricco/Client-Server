import './styles/CountDown.css';

export default class CountDown{
    constructor(){
        this.launchDate = new Date(2021, 8, 10);
    }

    isExpired(){
        return this.launchDate.getTime() < Date.now();
    }

    render(){
        return (
            <div>
                {blob}
            </div>
        );
    }

    printTime(){
        let time = document.getElementById("countDownTime");
        let remainingTimeSecs = Math.trunc((this.launchDate.getTime() - Date.now())/1000);
        let remainingTimeMins = Math.trunc(remainingTimeSecs/60);
        remainingTimeSecs%=60;
        let remainingTimeHours = Math.trunc(remainingTimeMins/60);
        remainingTimeMins%=60;
        let remainingTimeDays = Math.trunc(remainingTimeHours/24);
        remainingTimeHours%=24;
        time.innerHTML = remainingTimeDays+"d : "+remainingTimeHours+"h : "+remainingTimeMins+"m : "+remainingTimeSecs+"s";
    }

    startCountDown(){
        this.printTime();
        setInterval(()=>{
            this.printTime();
        }, 500);
    }
}

var blob = (
    <svg id="countDownSvg" className="fullSize" viewBox="0 0 960 540" xmlns="http://www.w3.org/2000/svg"
        version="1.1">        
        <g transform="translate(488.01180519292905 231.10176835738787)" visibility="visible">
            <path id="morph0"
                d="M125.6 -174.2C170.5 -166.6 219.9 -144.8 243.6 -107.1C267.3 -69.4 265.3 -15.8 259.2 38.4C253.1 92.5 242.9 147.1 211 181.4C179 215.7 125.3 229.7 73.2 241.3C21.1 253 -29.3 262.3 -72.6 248.8C-115.9 235.4 -152 199.2 -182.3 160.2C-212.6 121.2 -236.9 79.4 -256.7 28.9C-276.5 -21.7 -291.8 -80.9 -264.3 -112.2C-236.7 -143.4 -166.5 -146.6 -115.3 -151.3C-64.2 -156.1 -32.1 -162.4 4.1 -168.9C40.4 -175.3 80.7 -181.9 125.6 -174.2"
                fill="#c72433"></path>
        </g>
        <g transform="translate(515.9543569319067 295.6274933275688)" visibility="hidden">
            <path id="morph1"
                d="M90.8 -163.5C120.9 -122.1 150.5 -102.2 169.4 -73.6C188.4 -45 196.6 -7.8 202.5 37C208.4 81.9 211.9 134.2 187.4 162.3C162.9 190.3 110.5 194 60.4 211.3C10.2 228.7 -37.6 259.7 -73.3 249.4C-109.1 239 -132.7 187.3 -174 146.8C-215.2 106.3 -273.9 77.1 -278.4 40.5C-283 3.9 -233.4 -40 -208.3 -95.3C-183.3 -150.6 -182.9 -217.3 -152.3 -258.4C-121.6 -299.6 -60.8 -315.3 -15.2 -291.6C30.4 -268 60.8 -205 90.8 -163.5"
                fill="#c72433"></path>
        </g>
        <text id="countDownTime" transform="matrix(1.0499 0 0 1 260.5522 278.9202)" className="st4 st5 st6">SERVICES</text>
    </svg>
);