import React from 'react'

const Hero = () => {
  return (
    <div className='container p-5'>
        <div className="row">
            
            <img src='media/images/homeHero.png' alt='Hero section banner'className='mb-5'/>
            <h1 className='text-center mt-5'>Invest in everything</h1>
            <p className='text-center'>Online platform to invest in stocks, derivatives, mutual funds, ETFs, bonds, and more.</p>
            <button className='p-2 btn btn-primary btn-lg fs-5' style={{width:"17%",margin:"0 auto"}}>Sign up for free</button>
        </div>
      
    </div>
  )
}

export default Hero
