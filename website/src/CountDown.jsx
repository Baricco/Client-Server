import './styles/CountDown.css';

export default class CountDown{
    constructor(){
        this.launchDate = new Date("Oct 1, 2021 10:00:00");
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
        let now = new Date().getTime();
        let timeleft = this.launchDate.getTime() - now;

        let days = Math.floor(timeleft / (1000 * 60 * 60 * 24));
        let hours = Math.floor((timeleft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        let minutes = Math.floor((timeleft % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = Math.floor((timeleft % (1000 * 60)) / 1000);
        
        let remainingTime = ["a", "b", "c", "d"];
        console.log(remainingTime);
        console.log(days.toString());
        remainingTime[0] = (days < 10 ? "0" : "") + days.toString();
        console.log(remainingTime);

        remainingTime[1] = (hours < 10 ? "0" : "") + hours.toString();
        console.log(remainingTime);

        remainingTime[2] = (minutes < 10 ? "0" : "") + minutes.toString();
        console.log(remainingTime);

        remainingTime[3] = (seconds < 10 ? "0" : "") + seconds.toString();
        console.log(remainingTime);


        let daysWord = "days";

        if (days == 1) daysWord = "day";


        let remainingTimeString = remainingTime[1] + " : " + remainingTime[2] + " : " + remainingTime[3];
        document.getElementById("countDownDays").innerHTML = remainingTime[0] + " " + daysWord;
        document.getElementById("countDownTime").innerHTML = remainingTimeString;
    }

    startCountDown(){
        this.printTime();
        setInterval(()=>{
            this.printTime();
        }, 100);
    }
}

var blob = (
    <div>
        <div>
            <h2 className = "IntroductionTitle">We are Working on this site right now</h2>
            <p className = "IntroductionText"> This site is being developed by our team for you to let you download our messaging service, it's going to be a free app which will let you chat with your friends and with strangers securely and in an anonymous way, your messages are going to be encrypted and your identity will always be hidden from others, we care about your safety. </p>
            <h2 className = "IntroductionTitle">SITE LAUNCH:</h2>
            <h1 className = "SiteLaunch">1 October 2021</h1>
            <h1 className = "SiteLaunch">10:00 AM</h1>
            <h2 className = "IntroductionTitle">REMAINING TIME:</h2>
            <h1 id="time-left"></h1>
        </div>
        <br />
        <div style={{width:"100%", display:"flex", marginTop:"-5%"}}>
            <svg id="countDownSvg" preserveAspectRatio="none" viewBox="0 0 400 500" xmlns="http://www.w3.org/2000/svg" version="1.1">        
                <g transform="translate(192.25755887230034 236.64599620279608)" visibility="visible">
                    <path id="morph0" d="M98 -54.6C123.8 -11.8 139.4 38.8 121.4 82.6C103.3 126.3 51.7 163.2 6.1 159.7C-39.5 156.2 -79.1 112.3 -98.9 67.6C-118.6 22.8 -118.6 -22.8 -98.9 -62.1C-79.1 -101.3 -39.5 -134.2 -1.7 -133.2C36.1 -132.2 72.2 -97.3 98 -54.6" fill="#c72433"></path>
                </g>
                <g transform="translate(194.26254277067002 249.39708406856954)" visibility="hidden">
                    <path id = "morph1" d="M80.5 -74.3C113.8 -47.1 156.9 -23.6 167.9 11C178.8 45.5 157.6 91 124.3 114.8C91 138.6 45.5 140.8 8.4 132.5C-28.8 124.1 -57.5 105.2 -90 81.3C-122.5 57.5 -158.8 28.8 -159.3 -0.6C-159.9 -29.9 -124.9 -59.9 -92.4 -87C-59.9 -114.2 -29.9 -138.6 -3.2 -135.4C23.6 -132.2 47.1 -101.5 80.5 -74.3" fill="#BB004B"></path>
                </g>
                <text transform="matrix(1.0499 0 0 1 135 220)" id="countDownDays"></text>
                <text transform="matrix(1.0499 0 0 1 115 250)" id="countDownTime"></text>
            </svg>
        </div>
    </div>
);