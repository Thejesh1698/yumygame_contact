import React, {} from "react";

function ContactFeedbackComponent() {
    return (
        <>
            <button className="back-btn">
                <svg xmlns="https://www.w3.org/2000/svg" width="23" height="18" viewBox="0 0 23 18">
                    <path fill="#5C5F6E" fillRule="evenodd"
                          d="M21.7131495,8.30441922 L22.8392418,8.86746538 L21.7176501,9.42826122 L21.8216762,9.53228733 L14.0757875,17.278176 C13.4779181,17.8760455 12.5128049,17.8802703 11.9108381,17.2783035 C11.313039,16.6805044 11.3156571,15.7086626 11.9109656,15.113354 L16.6568542,10.3674654 L1.49116761,10.3674654 C0.667618478,10.3674654 0,9.70166814 0,8.86746538 C0,8.03903825 0.67145511,7.36746538 1.49116761,7.36746538 L16.6636052,7.36746538 L11.9109656,2.61482579 C11.3156571,2.01951728 11.313039,1.04767543 11.9108381,0.449876354 C12.5128049,-0.152090468 13.4779181,-0.147865648 14.0757875,0.450003834 L21.8216762,8.1958925 L21.7131495,8.30441922 Z"/>
                </svg>
            </button>
            <div className="right-wing">
                <div className="text">
                    <div className="text-header">Let’s talk.</div>
                    <p className="text-body">Share your excitement with us.<br/>
                        <a className="link link-animation white" id="contact" href="mailto:info@yummygum.com">
                    <span>info@yummygum.com
                        <svg xmlns="https://www.w3.org/2000/svg" width="14" height="12" viewBox="0 0 14 12">
                            <path className="arrow-vector" fill="#fff" fillRule="evenodd"
                                  d="M120.828427,16.6568542 L111,16.6568542 L111,18.6568542 L120.828427,18.6568542 L117.585786,21.8994949 L119,23.3137085 L124.656854,17.6568542 L123.949747,16.9497475 L119,12 L117.585786,13.4142136 L120.828427,16.6568542 Z"
                                  transform="translate(-111 -12)"/>
                        </svg>
                    </span>
                        </a>
                    </p>
                    <div className="feedback-info">
                        <h4 className="feedback-info-header">Let's talk about</h4>
                        <div className="feedback-radio-wrapper">
                            <ul>
                                <li className="feedback-radio">
                                    <label>
                                        <input type="radio" name="reason" value="Your great project" className="white"/>Your
                                        great project
                                    </label>
                                </li>
                                <li className="feedback-radio">
                                    <label>
                                        <input type="radio" name="reason" id="meeting-coffee"
                                               value="Meeting for a coffee"
                                               className="white"/>Meeting for a coffee
                                    </label>
                                </li>
                                <li className="feedback-radio">
                                    <label>
                                        <input type="radio" name="reason" id="birds-bees" value="Birds and bees"
                                               className="white"/>Birds and bees
                                    </label>
                                </li>
                                <li className="feedback-radio">
                                    <label>
                                        <input type="radio" name="reason" id="video-call"
                                               value="Plan a video call"
                                               className="white"/>Plan a video call
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <button type="button" className="next btn btn-primary btn-white" id="next-form">Next</button>
                    </div>
                </div>
                <div className="feedback-triangle"/>
            </div>
        </>
    );
}

export default ContactFeedbackComponent;
